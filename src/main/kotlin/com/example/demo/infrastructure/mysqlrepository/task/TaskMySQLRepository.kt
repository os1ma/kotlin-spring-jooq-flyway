package com.example.demo.infrastructure.mysqlrepository.task

import com.example.demo.domain.model.task.Task
import com.example.demo.domain.model.task.TaskId
import com.example.demo.domain.model.task.TaskName
import com.example.demo.domain.model.task.TaskRepository
import com.example.demo.infrastructure.jooq.generated.demo.tables.Tasks
import com.example.demo.infrastructure.jooq.generated.demo.tables.records.TasksRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class TaskMySQLRepository(val dsl: DSLContext) : TaskRepository {

    val taskTable: Tasks = Tasks.TASKS

    // TODO fetch した数が 1 つでない場合に対応
    override fun findById(id: TaskId): Task {
        val tasks = dsl.select(
                taskTable.ID,
                taskTable.NAME)
                .from(taskTable)
                .where(taskTable.ID.eq(id.value))
                .fetchInto(TasksRecord::class.java)

        return tasks.map { r ->
            Task(
                    TaskId(r.id),
                    TaskName(r.name))
        }
                .first()
    }

}