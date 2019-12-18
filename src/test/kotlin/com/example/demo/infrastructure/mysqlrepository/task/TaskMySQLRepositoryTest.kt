package com.example.demo.infrastructure.mysqlrepository.task

import com.example.demo.domain.model.task.Task
import com.example.demo.domain.model.task.TaskId
import com.example.demo.domain.model.task.TaskName
import com.example.demo.domain.model.task.Tasks
import com.example.demo.domain.model.user.UserId
import com.example.demo.domain.model.user.UserName
import com.example.demo.domain.model.user.UserNames
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

// TODO @SpringBootTest を使わないことでテストを高速化する
@Transactional
@SpringBootTest(properties = ["logging.level.org.jooq=DEBUG"])
internal class TaskMySQLRepositoryTest {

    companion object {
        private val EXPECTED_TASKS = Tasks.of(
                Task(
                        TaskId(1),
                        TaskName("Task1"),
                        UserName("Alice"),
                        UserNames(listOf("Bob", "Charlie").map { s -> UserName(s) })),
                Task(
                        TaskId(2),
                        TaskName("Task2"),
                        UserName("Bob"),
                        UserNames(listOf("Charlie").map { s -> UserName(s) })),
                Task(
                        TaskId(3),
                        TaskName("Task3"),
                        UserName("Bob"),
                        UserNames.ofEmpty())
        )

        private val INITIAL_TASK_COUNT = EXPECTED_TASKS.size()
    }

    @Autowired
    lateinit var repository: TaskMySQLRepository

    @Test
    fun test_findAll() {
        assertEquals(EXPECTED_TASKS, repository.findAll())
    }

    @Test
    fun test_findById() {
        // if exists
        val taskWitchExists = repository.findById(TaskId(1))
        assertEquals(EXPECTED_TASKS[0], taskWitchExists)

        // if not exist
        val taskWitchNotExist = repository.findById(TaskId(0))
        assert(taskWitchNotExist == null)
    }

    @Test
    fun test_size() {
        assertEquals(INITIAL_TASK_COUNT, repository.size())
    }

    @Test
    fun test_add_delete() {
        // add
        val taskName = TaskName("TestTask1")
        val assigneeUserId = UserId(1)

        val taskId = repository.add(taskName, assigneeUserId)
        assertEquals(INITIAL_TASK_COUNT + 1, repository.size())

        // remove
        repository.remove(taskId)
        assertEquals(INITIAL_TASK_COUNT, repository.size())
    }

}