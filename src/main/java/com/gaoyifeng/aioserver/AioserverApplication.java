package com.gaoyifeng.aioserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gaoyifeng.aioserver.infrastructure.dao.mapper")
public class AioserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AioserverApplication.class, args);
    }

}
