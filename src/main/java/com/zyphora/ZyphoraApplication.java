package com.zyphora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.scheduling.annotation.EnableAsync
public class ZyphoraApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZyphoraApplication.class, args);
    }
}