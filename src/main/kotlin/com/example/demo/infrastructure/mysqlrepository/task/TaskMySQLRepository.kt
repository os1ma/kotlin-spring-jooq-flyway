package com.example.demo.infrastructure.mysqlrepository.task

import com.example.demo.domain.model.task.Task
import com.example.demo.domain.model.task.TaskId
import com.example.demo.domain.model.task.TaskName
import com.example.demo.domain.model.task.TaskRepository
import com.example.demo.infrastructure.jooq.generated.demo.tables.TasksTable
import com.example.demo.infrastructure.jooq.generated.demo.tables.pojos.TasksEntity
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class TaskMySQLRepository(val dsl: DSLContext) : TaskRepository {

    private val taskTable: TasksTable = TasksTable.TASKS

    // TODO fetch した数が 1 つでない場合に対応
    override fun findById(id: TaskId): Task {
        val tasks = dsl.select(
                taskTable.ID,
                taskTable.NAME)
                .from(taskTable)
                .where(taskTable.ID.eq(id.value))
                .fetchInto(TasksEntity::class.java)

        return tasks.map { r -> Task(TaskId(r.id), TaskName(r.name)) }
                .first()
    }

}