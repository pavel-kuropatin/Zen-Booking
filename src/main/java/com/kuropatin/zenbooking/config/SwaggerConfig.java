package com.kuropatin.zenbooking.config;

import com.kuropatin.zenbooking.security.util.SecurityConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        final License license = new License()
                .name("Licensed under APACHE LICENSE, VERSION 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");
        final Contact contact = new Contact()
                .name("Pavel Kuropatin")
                .url("https://www.linkedin.com/in/pavel-kurapatsin/")
                .email("kuropatin.pv@gmail.com");
        final Info info = new Info()
                .title("Zen Booking API")
                .description("DISCLAIMER: " +
                        "Data security is not guaranteed. " +
                        "When using the application, do not enter any real personal information. " +
                        "You enter any personal information at your own risk.")
                .version("v1")
                .license(license)
                .contact(contact);
        final SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(SecurityConstants.X_AUTH_TOKEN);
        final Components components = new Components()
                .addSecuritySchemes("JWT Token", securityScheme);
        return new OpenAPI()
                .info(info)
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList("JWT Token"));
    }
}