package com.example.demo.application.service.task

import com.example.demo.domain.model.task.Task
import com.example.demo.domain.model.task.TaskId
import com.example.demo.domain.model.task.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskApplicationService(
        val taskRepository: TaskRepository) {

    fun get(id: TaskId): Task {
        return taskRepository.findById(id)
    }

}