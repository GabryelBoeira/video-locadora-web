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

    final String securitySchemeName = "bearerAuth";
    private static final String AUTH_URL = "http://localhost:8081/realms/video-locadora/protocol/openid-connect/auth";
    private static final String TOKEN_URL = "http://localhost:8081/realms/video-locadora/protocol/openid-connect/token";
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
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(components());
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(AUTH_URL)
                                                .tokenUrl(TOKEN_URL))));
    }

}
