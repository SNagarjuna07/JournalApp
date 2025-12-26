package com.edigest.journalApp.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customConfig() {
        return new OpenAPI().info(
            new Info().title("Journal App APIs")
                    .description("By S Nagarjuna")
        )
        .tags(Arrays.asList(
                new Tag().name("Public APIs").description("Signup, Login and health-check the app."),
                new Tag().name("Google login OAuth2 API").description("Enables users to login from Google using OAuth2."),
                new Tag().name("User APIs").description("Read, Update and Delete Users."),
                new Tag().name("Journal entry APIs").description("Add, Delete, Update and View journal entries."),
                new Tag().name("Admin APIs").description("Admin usable APIs for viewing users, journal entries.")
        ))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components().addSecuritySchemes(
                    "bearerAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .in(SecurityScheme.In.HEADER)
                            .name("Authorization")
        ));
    
    }
}
