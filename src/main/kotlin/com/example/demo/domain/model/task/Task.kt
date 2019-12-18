package com.example.demo.domain.model.task

import com.example.demo.domain.model.user.UserName
import com.example.demo.domain.model.user.UserNames

// aggregate
data class Task(
        val id: TaskId,
        val name: TaskName,
        val assigneeName: UserName,
        val reviewerNames: UserNames) {

    fun reviewerNameValues(): List<String> {
        return reviewerNames.list.map { n -> n.value }
    }
}

// value objects
data class TaskId(val value: Int)

data class TaskName(val value: String)

// first class collections
data class Tasks(val list: List<Task>) {

    fun firstOrNull(): Task? {
        return list.firstOrNull()
    }

    operator fun get(index: Int): Task {
        return list[index]
    }

    fun size(): Int {
        return list.size
    }

    companion object {

        fun of(vararg tasks: Task): Tasks {
            return Tasks(tasks.asList())
        }

    }

}