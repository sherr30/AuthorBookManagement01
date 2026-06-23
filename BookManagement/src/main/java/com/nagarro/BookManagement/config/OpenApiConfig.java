package com.nagarro.BookManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        String securitySchemeName = "basicAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Author & Book Management API")
                        .version("1.0")
                        .description("Book Management System with Role Based Security"))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")));
    }
}