package com.learnspring.fluxgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FluxGateApplication {

    public static void main(String[] args) {
        String port = System.getenv("PORT");
        if (port == null || port.isBlank()) {
            port = "8080";
        }
        System.setProperty("server.port", port);
        SpringApplication.run(FluxGateApplication.class, args);
    }

}