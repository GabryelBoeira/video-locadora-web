package io.github.gabryel.bff.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI bffOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BFF - Video Locadora")
                        .description("Backend For Frontend: autenticacao via Keycloak e orquestracao de chamadas ao video-locadora")
                        .version("v1"));
    }

}
