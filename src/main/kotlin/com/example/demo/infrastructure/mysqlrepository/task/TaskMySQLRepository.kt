package com.example.demo.infrastructure.mysqlrepository.task

import com.example.demo.domain.model.task.*
import com.example.demo.infrastructure.jooq.generated.demo.tables.TasksTable
import com.example.demo.infrastructure.jooq.generated.demo.tables.pojos.TasksEntity
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class TaskMySQLRepository(val ctx: DSLContext) : TaskRepository {

    private val taskTable: TasksTable = TasksTable.TASKS

    override fun findAll(): Tasks {
        val entities = ctx.select(
                taskTable.ID,
                taskTable.NAME)
                .from(taskTable)
                .fetchInto(TasksEntity::class.java)

        val list = entities.map { e -> entity2model(e) }

        return Tasks(list)
    }

    override fun findById(id: TaskId): Task? {
        val entities = ctx.select(
                taskTable.ID,
                taskTable.NAME)
                .from(taskTable)
                .where(taskTable.ID.eq(id.value))
                .fetchInto(TasksEntity::class.java)

        return entities.map { e -> entity2model(e) }
                .firstOrNull()
    }

    override fun add(name: TaskName): TaskId {
        val record = ctx.newRecord(taskTable)

        record.name = name.value

        record.store()

        return TaskId(record.id)
    }

    private fun entity2model(e: TasksEntity): Task {
        return Task(TaskId(e.id), TaskName(e.name))
    }

}