package com.example.demo.infrastructure.mysqlquery.health

import com.example.demo.application.query.health.DBHealthQueryService
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class DBHealthMySQLQueryService(
        private val ctx: DSLContext) : DBHealthQueryService {

    override fun isHealthy(): Boolean {
        ctx.selectOne().fetch()
        // 例外が発生しなければ正常
        return true
    }

}