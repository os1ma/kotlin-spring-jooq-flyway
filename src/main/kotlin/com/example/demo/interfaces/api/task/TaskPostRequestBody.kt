package com.example.demo.interfaces.api.task

import com.example.demo.domain.model.task.TaskName
import com.example.demo.domain.model.user.UserId

data class TaskPostRequestBody(
        var name: String,
        var assigneeUserId: Int) {

    fun taskName(): TaskName {
        return TaskName(name)
    }

    fun assigneeUserId(): UserId {
        return UserId(assigneeUserId)
    }

}