package com.example.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cho phép tất cả các đường dẫn
                .allowedOrigins("*") // Cho phép mọi nguồn gọi vào (đơn giản cho demo)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Các method cho phép
    }
}