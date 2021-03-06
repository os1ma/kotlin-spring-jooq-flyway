package com.example.demo.infrastructure.spring.fox

import com.google.common.base.Predicates.not
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

private const val SPRING_FOX_ENABLED_PROFILE = "local"

@Profile(SPRING_FOX_ENABLED_PROFILE)
@Configuration
@EnableSwagger2
class SpringFoxConfig {

    @Bean
    fun document(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                // ignore /error paths which generated by Spring
                .paths(not(regex("/error")))
                .build()
                // delete default response status
                .useDefaultResponseMessages(false)
                // setting to use ResponseEntity
                .genericModelSubstitutes(ResponseEntity::class.java)
    }

}

/**
 * SpringFox を有効化するプロファイルでない場合に Swagger UI を完全に無効化する設定
 *
 * @see [How to fully disable swagger-ui in spring-boot?(/swagger-ui.html should return 404)]
 * (https://stackoverflow.com/questions/46454473/how-to-fully-disable-swagger-ui-in-spring-boot-swagger-ui-html-should-return-4)
 */
@Profile("!${SPRING_FOX_ENABLED_PROFILE}")
@Controller
class DisableSwaggerUiController {
    @GetMapping("/swagger-ui.html")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound() {
        // Do nothing
    }
}

