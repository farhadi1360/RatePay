package com.ratepay.bugtracker.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("spring_oauth", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("Oauth2 flow")
                                .flows(new OAuthFlows()

                                        .password(new OAuthFlow()
                                                .tokenUrl("http://localhost:7002" + "/oauth/token")

                                        )
                                )
                        )
                )
                .security(Arrays.asList(
                        new SecurityRequirement().addList("spring_oauth")))
                .info(new Info()
                        .title("Ratepay BugTracker  APIs")
                        .description("New Service From RatePay For BugTracker")
                        .termsOfService("terms")
                        .contact(new Contact().email("farhadi.kam@gmail.com").name("Developer: Mostafa Farhadi"))
                        .license(new License().name("GNU"))
                        .version("2.0")
                );
    }
}
