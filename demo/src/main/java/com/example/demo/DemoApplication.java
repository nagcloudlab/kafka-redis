package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootApplication
@RestController
public class DemoApplication {

	 String helloCount = "0";

	@GetMapping("/hello")
	public String sayHello() {
		int currentCount = Integer.parseInt(helloCount);
		currentCount++;
		helloCount = String.valueOf(currentCount);
		return "hello client (" + currentCount + ")";
	}
	

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
