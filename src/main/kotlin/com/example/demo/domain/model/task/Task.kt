package com.example.demo.domain.model.task

data class Tasks(val list: List<Task>)

// aggregate
data class Task(val id: TaskId, val name: TaskName)

data class TaskId(val value: Int)
data class TaskName(val value: String)
