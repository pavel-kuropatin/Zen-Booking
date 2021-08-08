package com.kuropatin.bookingapp.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.kuropatin.bookingapp.util.CacheNames;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CacheNames.ORDER, CacheNames.PROPERTY_IMAGE, CacheNames.PROPERTY, CacheNames.USER, CacheNames.BOOLEAN);
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