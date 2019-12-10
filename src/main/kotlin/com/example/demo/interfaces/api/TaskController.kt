package com.example.demo.interfaces.api

import com.example.demo.application.service.task.TaskApplicationService
import com.example.demo.domain.model.task.TaskId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
class TaskController(
        val service: TaskApplicationService) {

    @GetMapping("/{taskId}")
    fun get(@PathVariable taskId: Int): TaskGetResponse {
        val task = service.get(TaskId(taskId))
        return TaskGetResponse.from(task)
    }

}