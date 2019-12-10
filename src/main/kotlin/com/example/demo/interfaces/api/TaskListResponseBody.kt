package com.example.demo.interfaces.api

import com.example.demo.domain.model.task.Tasks

data class TaskListResponseBody(
        val tasks: List<TaskGetResponseBody>
) {

    companion object {
        fun from(tasks: Tasks): TaskListResponseBody {
            val list = tasks.list.map { t -> TaskGetResponseBody.from(t) }
            return TaskListResponseBody(list)
        }
    }
}