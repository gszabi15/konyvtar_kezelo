package org.gszabi15.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI libraryApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Library Management API")
                .description("REST API documentation for managing books in the library system.")
                .version("1.0.0")
            );
    }
}