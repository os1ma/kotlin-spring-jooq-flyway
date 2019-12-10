package com.example.demo.interfaces.api

import com.example.demo.application.service.task.TaskApplicationService
import com.example.demo.domain.model.task.TaskId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
        private val service: TaskApplicationService) {

    @GetMapping()
    fun list(): ResponseEntity<TaskListResponseBody> {
        val tasks = service.list()
        val body = TaskListResponseBody.from(tasks)
        return ResponseEntity.ok(body)
    }

    @GetMapping("/{taskId}")
    fun get(@PathVariable taskId: Int): ResponseEntity<TaskGetResponseBody> {
        val task = service.get(TaskId(taskId)) ?: return ResponseEntity.notFound().build()
        val body = TaskGetResponseBody.from(task)
        return ResponseEntity.ok(body)
    }

    @PostMapping
    fun post(@RequestBody body: TaskPostRequestBody) {
        service.register(body.taskName())
    }

}