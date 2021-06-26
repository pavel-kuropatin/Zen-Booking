package com.kuropatin.bookingapp.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwtconfig")
@Data
public class JwtConfig {

    private String secret;
    private Integer expiration;
}