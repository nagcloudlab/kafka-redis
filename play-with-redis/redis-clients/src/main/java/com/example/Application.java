package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/")
    public String home() {
        Long counter = redisTemplate.opsForValue().increment("counter", 1);
        return "Hello World! This page has been viewed " + counter + " times.";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
