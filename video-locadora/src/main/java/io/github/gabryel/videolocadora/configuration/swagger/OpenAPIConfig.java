package io.github.gabryel.videolocadora.configuration.swagger;

import io.github.gabryel.videolocadora.configuration.Messages;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Autowired
    private Messages messages;

    @Bean
    public OpenAPI myOpenAPI() {
        License license = new License();
        license.setName(messages.getMessage("swagger.license.description"));
        license.setUrl(messages.getMessage("swagger.license.url"));

        Info info = new Info()
                .title(messages.getMessage("swagger.title"))
                .description(messages.getMessage("swagger.description"))
                .termsOfService(messages.getMessage("swagger.terms.service"))
                .version(messages.getMessage("swagger.version"))
                .license(license);

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(components());
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }

}
