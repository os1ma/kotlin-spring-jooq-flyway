package com.example.demo.interfaces.api.task

import com.example.demo.application.service.task.TaskApplicationService
import com.example.demo.domain.model.task.TaskId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
        private val service: TaskApplicationService) {

    @GetMapping()
    fun list(): TaskListResponseBody {
        val tasks = service.list()
        return TaskListResponseBody.from(tasks)
    }

    @GetMapping("/{taskId}")
    fun get(@PathVariable taskId: Int): ResponseEntity<TaskGetResponseBody> {
        val task = service.get(TaskId(taskId)) ?: return ResponseEntity.notFound().build()
        val body = TaskGetResponseBody.from(task)
        return ResponseEntity.ok(body)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody body: TaskPostRequestBody) {
        service.register(
                body.taskName(),
                body.assigneeUserId())
    }

}