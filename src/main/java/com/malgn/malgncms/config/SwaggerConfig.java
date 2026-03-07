package com.malgn.malgncms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.malgn.malgncms.config
 * fileName       : SwaggerConfig
 * author         : JAEIK
 * date           : 3/6/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/6/26        JAEIK       최초 생성
 */
@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(SECURITY_SCHEME_NAME);

        return new OpenAPI()
                .info(new Info()
                        .title("MalganCMS")
                        .description("MalganCMS API 문서")
                        .version("v1.0"))
                .addSecurityItem(securityRequirement)
                .components(new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme));
    }
}
