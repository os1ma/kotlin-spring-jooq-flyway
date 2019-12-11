package com.example.demo.application.service.health

import com.example.demo.application.query.health.DBHealthQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class HealthApplicationService(
        private val dbHealthCheckService: DBHealthQueryService) {

    fun isHealthy(): Boolean {
        return dbHealthCheckService.isHealthy()
    }

}