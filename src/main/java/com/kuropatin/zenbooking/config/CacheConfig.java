package com.kuropatin.zenbooking.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.kuropatin.zenbooking.util.CacheNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${cache.config.initial-capacity}")
    private int initialCapacity;

    @Value("${cache.config.maximum-size}")
    private int maximumSize;

    @Value("${cache.config.expiration-time-in-seconds}")
    private int expirationTimeInSeconds;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CacheNames.getCacheNames());
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    private Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterAccess(expirationTimeInSeconds, TimeUnit.SECONDS)
                .weakKeys()
                .recordStats();
    }
}