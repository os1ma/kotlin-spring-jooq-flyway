package com.example.demo.domain.model.task

interface TaskRepository {

    fun findById(id: TaskId): Task

}