package com.funfair.api.common;


import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .servers(List.of(
                		  new Server().url("https://b5b6-103-85-11-148.ngrok-free.app").description("Local Server"),
                          new Server().url("https://b5b6-103-85-11-148.ngrok-free.app").description("Production Server")

                ));
    }
}