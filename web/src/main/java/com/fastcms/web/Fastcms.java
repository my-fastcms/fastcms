package com.fastcms.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.fastcms")
@EnableScheduling
public class Fastcms {

    public static void main(String[] args) {
        SpringApplication.run(Fastcms.class, args);
    }

}
