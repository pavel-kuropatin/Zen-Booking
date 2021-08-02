package com.kuropatin.bookingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kuropatin.bookingapp")
public class BookingAppStarter {

    public static void main(String[] args) {
        SpringApplication.run(BookingAppStarter.class, args);
    }
}