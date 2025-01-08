package com.github.krzkuc1985.rest;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${project.version}")
    private String projectVersion;

    @Value("${project.description}")
    private String projectDescription;

    @Value("${project.name}")
    private String projectName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(components())
                .security(security())
                .info(apiInfo())
                .servers(servers());
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes("BearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }

    private List<SecurityRequirement> security() {
        return List.of(
                new SecurityRequirement().addList("BearerAuth")
        );
    }

    private Info apiInfo() {
        return new Info()
                .title(projectName)
                .description(projectDescription)
                .version(projectVersion);
    }

    private List<Server> servers() {
        return List.of(
                new Server().url("http://localhost:8080").description("Development server")
        );
    }
}
