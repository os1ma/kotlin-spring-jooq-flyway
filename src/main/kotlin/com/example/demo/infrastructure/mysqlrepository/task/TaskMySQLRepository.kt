package com.example.demo.infrastructure.mysqlrepository.task

import com.example.demo.domain.model.task.*
import com.example.demo.domain.model.user.UserName
import com.example.demo.infrastructure.jooq.generated.demo.tables.TASKS_TABLE
import com.example.demo.infrastructure.jooq.generated.demo.tables.USERS_TABLE
import org.jooq.DSLContext
import org.jooq.Record3
import org.jooq.SelectOnConditionStep
import org.springframework.stereotype.Repository

@Repository
class TaskMySQLRepository(private val ctx: DSLContext) : TaskRepository {

    private val tasksTable: TASKS_TABLE = TASKS_TABLE.TASKS
    private val usersTable: USERS_TABLE = USERS_TABLE.USERS

    override fun findAll(): Tasks {
        val entities = select()
                .fetch()

        val list = entities.map { e -> record2model(e) }

        return Tasks(list)
    }

    override fun findById(id: TaskId): Task? {
        val entities = select()
                .where(tasksTable.ID.eq(id.value))
                .fetch()

        return entities.map { e -> record2model(e) }
                .firstOrNull()
    }

    // FIXME fix to save assignee
    override fun add(name: TaskName): TaskId {
        val record = ctx.newRecord(tasksTable)

        record.name = name.value

        record.store()

        return TaskId(record.id)
    }

    // private funs

    private fun select(): SelectOnConditionStep<Record3<Int, String, String>> {
        return ctx.select(
                tasksTable.ID,
                tasksTable.NAME,
                usersTable.NAME)
                .from(tasksTable)
                .innerJoin(usersTable)
                .on(usersTable.ID.eq(tasksTable.ASIGNEE_USER_ID))
    }

    private fun record2model(r: Record3<Int, String, String>): Task {
        return Task(
                TaskId(r[tasksTable.ID]),
                TaskName(r[tasksTable.NAME]),
                UserName(r[usersTable.NAME]))
    }

}