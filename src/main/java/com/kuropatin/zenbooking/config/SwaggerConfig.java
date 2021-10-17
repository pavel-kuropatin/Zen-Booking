package com.kuropatin.zenbooking.config;

import com.kuropatin.zenbooking.security.util.CustomHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kuropatin.zenbooking"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Zen Booking API")
                .description("API documentation.\n" +
                        "DISCLAIMER: " +
                        "Data security is not guaranteed. " +
                        "When using the application, do not enter any real personal information. " +
                        "You enter any personal information at your own risk.")
                .version("v0.1.0-SNAPSHOT")
                .license("Licensed under APACHE LICENSE, VERSION 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Pavel Kuropatin", "https://www.linkedin.com/in/pavel-kurapatsin/", "kuropatin.pv@gmail.com"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT Token", CustomHeaders.X_AUTH_TOKEN, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(defaultAuth()))
                .build();
    }

    private SecurityReference defaultAuth() {
        return SecurityReference.builder()
                .scopes(new AuthorizationScope[]{
                                new AuthorizationScope("global", "accessEverything")
                })
                .reference("JWT Token")
                .build();
    }
}