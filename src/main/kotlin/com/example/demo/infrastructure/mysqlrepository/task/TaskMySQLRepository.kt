package com.example.demo.infrastructure.mysqlrepository.task

import com.example.demo.domain.model.task.*
import com.example.demo.domain.model.user.UserId
import com.example.demo.domain.model.user.UserName
import com.example.demo.domain.model.user.UserNames
import com.example.demo.infrastructure.jooq.generated.demo.tables.REVIEWERS_TABLE
import com.example.demo.infrastructure.jooq.generated.demo.tables.TASKS_TABLE
import com.example.demo.infrastructure.jooq.generated.demo.tables.USERS_TABLE
import com.example.demo.infrastructure.jooq.generated.demo.tables.records.TasksRecord
import org.jooq.DSLContext
import org.jooq.Record4
import org.jooq.Result
import org.jooq.SelectOnConditionStep
import org.springframework.stereotype.Repository

@Repository
class TaskMySQLRepository(private val ctx: DSLContext) : TaskRepository {

    // private vals

    private val t = TASKS_TABLE.TASKS.`as`("t")
    private val u = USERS_TABLE.USERS.`as`("u")
    private val r = REVIEWERS_TABLE.REVIEWERS.`as`("r")
    private val ru = u.`as`("ru")

    // public funs

    override fun findAll(): Tasks {
        val records = select()
                .fetchGroups(t)

        return records2models(records)
    }

    override fun findById(id: TaskId): Task? {
        val records = select()
                .where(t.ID.eq(id.value))
                .fetchGroups(t)

        return records2models(records)
                .firstOrNull()
    }

    override fun size(): Int {
        return ctx.selectCount()
                .from(t)
                .fetchOne(0, Int::class.java)

    }

    override fun add(name: TaskName, assigneeUserId: UserId): TaskId {
        val record = ctx.newRecord(t)

        record.name = name.value
        record.assigneeUserId = assigneeUserId.value

        record.store()

        return TaskId(record.id)
    }

    override fun remove(id: TaskId) {
        ctx.delete(t)
                .where(t.ID.eq(id.value))
                .execute()
    }

    // private funs

    private fun select(): SelectOnConditionStep<RecordType> {
        return ctx.select(
                t.ID,
                t.NAME,
                u.NAME,
                ru.NAME)
                .from(t)
                .innerJoin(u).on(u.ID.eq(t.ASSIGNEE_USER_ID))
                .leftJoin(r).on(r.TASK_ID.eq(t.ID))
                .leftJoin(ru).on(ru.ID.eq(r.USER_ID))
    }

    private fun records2models(map: RecordMapType): Tasks {
        val list = map.map { r -> record2model(r) }
        return Tasks(list)
    }

    private fun record2model(entry: RecordMapEntryType): Task {
        val row = entry.value.first()

        val userNameList = entry.value
                .filter { v -> v[ru.NAME] != null }
                .map { v -> UserName(v[ru.NAME]) }

        return Task(
                TaskId(row[t.ID]),
                TaskName(row[t.NAME]),
                UserName(row[u.NAME]),
                UserNames(userNameList))
    }

}

private typealias RecordMapType = Map<TasksRecord, Result<RecordType>>

private typealias RecordMapEntryType = Map.Entry<TasksRecord, Result<RecordType>>

private typealias RecordType = Record4<Int, String, String, String>