package com.example.demo.interfaces.api

import com.example.demo.domain.model.task.Task

data class TaskGetResponse(
        val id: Int,
        val name: String
) {

    companion object {
        fun from(task: Task): TaskGetResponse {
            return TaskGetResponse(task.id.value, task.name.value)
        }
    }
}