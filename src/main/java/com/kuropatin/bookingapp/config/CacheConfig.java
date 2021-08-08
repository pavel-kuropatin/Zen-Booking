package com.kuropatin.bookingapp.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String ORDER = "orders";
    public static final String PROPERTY_IMAGE = "propertyImage";
    public static final String PROPERTY = "property";
    public static final String USER = "users";
    public static final String BOOLEAN = "boolean";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(ORDER, PROPERTY_IMAGE, PROPERTY, USER, BOOLEAN);
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    public Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(50)
                .expireAfterAccess(600, TimeUnit.SECONDS)
                .weakKeys()
                .recordStats();
    }
}