package com.example.userservicemay25;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceMay25Application {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceMay25Application.class, args);
    }

}
