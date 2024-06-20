package com.emi.store.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Electronic Store API")
                .version("1.0")
                .description("API desarrollada con SpringBoot en Java. Permite administrar los usuarios, productos y categorias de la tienda.")
                .termsOfService("http://swagger.io/terms/"));
    }
}
