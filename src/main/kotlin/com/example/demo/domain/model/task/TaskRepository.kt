package com.example.demo.domain.model.task

interface TaskRepository {

    fun findAll(): Tasks

    fun findById(id: TaskId): Task?

    // TODO add の引数を Task にするか検討
    fun add(name: TaskName): TaskId

}