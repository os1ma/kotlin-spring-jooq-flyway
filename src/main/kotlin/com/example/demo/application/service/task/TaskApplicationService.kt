package com.example.demo.application.service.task

import com.example.demo.domain.model.task.*
import com.example.demo.domain.model.user.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskApplicationService(
        private val taskRepository: TaskRepository) {

    fun list(): Tasks {
        return taskRepository.findAll()
    }

    fun get(id: TaskId): Task? {
        return taskRepository.findById(id)
    }

    fun register(name: TaskName, assigneeUserId: UserId) {
        taskRepository.add(name, assigneeUserId)
    }

    fun remove(id: TaskId) {
        taskRepository.remove(id)
    }

}