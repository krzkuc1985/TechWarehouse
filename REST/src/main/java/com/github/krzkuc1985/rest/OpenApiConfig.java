package com.github.krzkuc1985.rest;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
                .components(new Components())
                .info(new Info()
                        .title(projectName)
                        .description(projectDescription)
                        .version(projectVersion))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development server")
                ));
    }
}
