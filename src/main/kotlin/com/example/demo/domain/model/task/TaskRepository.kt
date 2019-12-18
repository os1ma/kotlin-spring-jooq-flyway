package com.example.demo.domain.model.task

import com.example.demo.domain.model.user.UserId

interface TaskRepository {

    // query

    fun findAll(): Tasks

    fun findById(id: TaskId): Task?

    fun size(): Int

    // command

    // TODO add の引数を Task にするか検討
    fun add(name: TaskName, assigneeUserId: UserId): TaskId

    fun remove(id: TaskId)

}