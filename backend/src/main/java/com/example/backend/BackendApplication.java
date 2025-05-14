package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableFeignClients
public class BackendApplication {

    public static void main(String[] args) {
        // Nạp file .env
        Dotenv dotenv = Dotenv.load();

        // Đẩy các biến vào System properties để Spring Boot có thể đọc được
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
        System.setProperty("JWT_SIGNER_KEY", dotenv.get("JWT_SIGNER_KEY"));

        // Chạy ứng dụng Spring Boot
        SpringApplication.run(BackendApplication.class, args);
    }
}