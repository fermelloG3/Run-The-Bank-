package io.github.fermelloG3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API RunTheBank")
                        .version("1.0.0")
                        .license(new License()
                                .name("Licença do Sistema")
                        .url("https://github.com/fermelloG3/Run-The-Bank-"))
        );

    }

    }

