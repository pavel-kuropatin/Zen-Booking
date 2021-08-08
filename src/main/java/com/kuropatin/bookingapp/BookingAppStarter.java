package com.kuropatin.bookingapp;

import com.kuropatin.bookingapp.config.CacheConfig;
import com.kuropatin.bookingapp.config.SwaggerConfig;
import com.kuropatin.bookingapp.security.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.kuropatin.bookingapp")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@Import({
        SecurityConfig.class,
        CacheConfig.class,
        SwaggerConfig.class
})
public class BookingAppStarter {

    public static void main(String[] args) {
        SpringApplication.run(BookingAppStarter.class, args);
    }
}