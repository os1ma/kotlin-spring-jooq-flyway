package com.example.demo.interfaces.api.health

import com.example.demo.application.service.health.HealthApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController(
        private val service: HealthApplicationService) {

    @GetMapping
    fun get(): ResponseEntity<Unit> {
        val status = if (service.isHealthy()) {
            HttpStatus.OK
        } else {
            HttpStatus.SERVICE_UNAVAILABLE
        }

        return ResponseEntity.status(status).build()
    }

}