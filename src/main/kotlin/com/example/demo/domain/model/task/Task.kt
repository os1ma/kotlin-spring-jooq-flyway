package com.example.demo.domain.model.task

import com.example.demo.domain.model.user.UserName

data class Tasks(val list: List<Task>)

// aggregate
data class Task(
        val id: TaskId,
        val name: TaskName,
        val assigneeName: UserName)

data class TaskId(val value: Int)
data class TaskName(val value: String)
