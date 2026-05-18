package com.example.week07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Week07Application {

    public static void main(String[] args) {
        SpringApplication.run(Week07Application.class, args);
    }
}