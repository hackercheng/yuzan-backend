package com.yupi.yuzan.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfiguration {

    @Bean
    public OpenAPI openapi() {
        return new OpenAPI()
                .info(new Info().title("api接口文档")
                        .description("api接口文档")
                        .version("1.0.0")
                        .contact(new Contact().name("yuzan")
                                .email("1648814890@qq.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("api接口文档")
                        .url("https://localhost:8080/api"));
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder().group("优赞项目接口文档")
                .pathsToMatch("/**")
                .build();
    }
}
