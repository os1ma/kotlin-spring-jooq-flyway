package com.example.demo.infrastructure.spring.fox

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

private const val PROTOCOL = "http"
private const val HOST = "localhost"
private const val SWAGGER_DOCS_API_PATH = "/v2/api-docs"
private const val SWAGGER_UI_PATH = "/swagger-ui.html"

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringFoxConfigEnabledTest {

    private val restTemplate = RestTemplate()

    @LocalServerPort
    private var port: Int? = null

    @Test
    fun swaggerDocsApiEnabledIfProfileIsLocal() {
        val url = "${PROTOCOL}://${HOST}:${port}${SWAGGER_DOCS_API_PATH}"
        val response = restTemplate.getForEntity(url, Any::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
    }

    @Test
    fun swaggerUiEnabledIfProfileIsLocal() {
        val url = "${PROTOCOL}://${HOST}:${port}${SWAGGER_UI_PATH}"
        val response = restTemplate.getForEntity(url, String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
    }

}

@ActiveProfiles("default")
// @LocalServerPort でポート番号を取得するための設定
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringFoxConfigDisabledTest {

    private val restTemplate = RestTemplate()

    @LocalServerPort
    private var port: Int? = null

    @Test
    fun swaggerDocsApiDisabledIfProfileIsNotLocal() {
        val url = "${PROTOCOL}://${HOST}:${port}${SWAGGER_DOCS_API_PATH}"
        assertThrows<HttpClientErrorException.NotFound> {
            restTemplate.getForEntity(url, Any::class.java)
        }
    }

    @Test
    fun swaggerUiDisabledIfProfileIsNotLocal() {
        val url = "${PROTOCOL}://${HOST}:${port}${SWAGGER_UI_PATH}"
        assertThrows<HttpClientErrorException.NotFound> {
            restTemplate.getForEntity(url, String::class.java)
        }
    }

}
