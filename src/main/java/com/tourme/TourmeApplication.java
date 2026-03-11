package com.tourme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourme.models.Tourist;
import com.tourme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
@RestController
public class TourmeApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TourmeApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "Welcome to Tourme!";
	}

	@PostMapping("/hello")
	public String save() {
		Tourist t = new Tourist("John Doe", "john.doe@example.com", "password123");
		userRepository.save(t);

		return "Tourist saved successfully!";
	}

}
