package com.airgear.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing OpenAPI documentation using Springdoc-OpenAPI.
 *
 * <p>This class provides a custom OpenAPI bean with a Bearer token security scheme and security requirements.
 * The OpenAPI documentation includes information about the "Air Gear API".</p>
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                .info(new Info().title("Air gear API"));
    }
}
