package com.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Indexed;

@Indexed
@SpringBootApplication
public class SocialCircleBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialCircleBackendApplication.class, args);
    }

}
