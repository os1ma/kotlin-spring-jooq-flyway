package com.example.demo.infrastructure.mysqlrepository.task

import com.example.demo.domain.model.task.Task
import com.example.demo.domain.model.task.TaskId
import com.example.demo.domain.model.task.TaskName
import com.example.demo.domain.model.task.TaskRepository
import org.springframework.stereotype.Repository

@Repository
class TaskInMemoryRepository: TaskRepository {

    override fun findById(id: TaskId): Task {
        return Task(TaskId(1), TaskName("sample"))
    }

}