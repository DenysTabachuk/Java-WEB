package com.example.space_cats.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "My Space Cats Marketplace",
                description = "Some long and useful description",
                version = "v1"
        )
)
public class OpenApiConfig {
        // add extra setting
}
