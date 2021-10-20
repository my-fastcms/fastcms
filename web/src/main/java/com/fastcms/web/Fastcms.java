package com.fastcms.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fastcms")
public class Fastcms {

    public static void main(String[] args) {
        SpringApplication.run(Fastcms.class, args);
    }

}
