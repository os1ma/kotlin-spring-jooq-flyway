package com.example.demo.interfaces.api

import com.example.demo.domain.model.task.Task

data class TaskGetResponseBody(
        val id: Int,
        val name: String,
        val assignee: String) {

    companion object {
        fun from(task: Task): TaskGetResponseBody {
            return TaskGetResponseBody(
                    task.id.value,
                    task.name.value,
                    task.assigneeName.value)
        }
    }
}