package com.example.demo.interfaces.api.task

import com.example.demo.domain.model.task.TaskName

data class TaskPostRequestBody(
        var name: String) {

    fun taskName(): TaskName {
        return TaskName(name)
    }
}