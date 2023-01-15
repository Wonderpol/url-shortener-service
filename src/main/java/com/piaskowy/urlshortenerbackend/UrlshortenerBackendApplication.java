package com.piaskowy.urlshortenerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UrlshortenerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlshortenerBackendApplication.class, args);
    }

}
