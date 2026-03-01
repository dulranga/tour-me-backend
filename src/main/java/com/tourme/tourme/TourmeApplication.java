package com.tourme.tourme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TourmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourmeApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "Welcome to Tourme!";
	}

}
