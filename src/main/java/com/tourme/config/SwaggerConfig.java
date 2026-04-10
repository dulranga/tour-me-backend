package com.tourme.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("TourMe API")
                                                .version("1.0.0")
                                                .description("A bidding platform for finding the best deals when travelling in Sri Lanka")
                                                .contact(new Contact()
                                                                .name("TourMe Support")
                                                                .email("support@tourme.com")
                                                                .url("https://tourme.com"))
                                                .license(new License()
                                                                .name("MIT License")
                                                                .url("https://opensource.org/licenses/MIT")))
                                .addServersItem(new Server()
                                                .url("http://localhost:8010")
                                                .description("Local Development Server"))
                                .addServersItem(new Server()
                                                .url("https://api.tourme.com")
                                                .description("Production Server"));
        }
}
