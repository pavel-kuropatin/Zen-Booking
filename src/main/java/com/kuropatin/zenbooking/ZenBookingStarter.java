package com.kuropatin.zenbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.kuropatin.zenbooking")
@EntityScan(basePackages = "com.kuropatin.zenbooking")
@EnableJpaRepositories(basePackages = "com.kuropatin.zenbooking")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@EnableTransactionManagement
public class ZenBookingStarter {

    public static void main(String[] args) {
        SpringApplication.run(ZenBookingStarter.class, args);
    }
}