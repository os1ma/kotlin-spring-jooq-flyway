package com.example.demo.domain.model.task

// aggregate
data class Task(val id: TaskId, val name: TaskName)

data class TaskId(val value: Int)
data class TaskName(val value: String)
