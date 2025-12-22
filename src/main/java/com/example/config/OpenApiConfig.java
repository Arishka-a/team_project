package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация OpenAPI/Swagger документации.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String description = "REST API для математического калькулятора "
                + "с поддержкой базовых операций и тригонометрических функций";
        return new OpenAPI()
                .info(new Info()
                        .title("Calculator API")
                        .version("1.0")
                        .description(description)
                        .contact(new Contact()
                                .name("Team Project")
                                .email("team@example.com")));
    }
}
