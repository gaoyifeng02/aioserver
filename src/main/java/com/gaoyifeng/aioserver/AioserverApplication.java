package com.gaoyifeng.aioserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gaoyifeng.aioserver")
public class AioserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AioserverApplication.class, args);
    }

}