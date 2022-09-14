package com.sombra.promotion.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        String schema = "bearerAuth";
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Course Management System API")
                .description("Course Management System :)")
                .version("v0.0.1")
                .license(new License().name("Apache 2.0"))
        );
        openAPI.addSecurityItem(new SecurityRequirement().addList(schema));
        openAPI.components(new Components()
                .addSecuritySchemes(schema, new SecurityScheme()
                        .name(schema)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
        );
        return openAPI;
    }

}
