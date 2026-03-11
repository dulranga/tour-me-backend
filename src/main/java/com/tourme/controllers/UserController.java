package com.tourme.controllers;

import com.tourme.models.Administrator;
import com.tourme.models.Driver;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users in the system (Tourists, Drivers, and Administrators)
     */
    @GetMapping
    public List<User> getAllUsers() {
        return null;
    }

    /**
     * Get a specific user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return null;
    }

    /**
     * Register a new Tourist
     */
    @PostMapping("/register/tourist")
    public Tourist registerTourist(@RequestBody Tourist tourist) {
        return null;
    }

    /**
     * Register a new Driver
     */
    @PostMapping("/register/driver")
    public Driver registerDriver(@RequestBody Driver driver) {
        return null;
    }

    /**
     * Register a new Administrator
     */
    @PostMapping("/register/admin")
    public Administrator registerAdmin(@RequestBody Administrator admin) {
        return null;
    }


    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(@PathVariable int id, @RequestBody User profileData) {
        return null;
    }


    @PutMapping("/{id}/vehicle")
    public ResponseEntity<?> updateVehicleDetails(@PathVariable int id, @RequestBody String vehicleDetails) {
        return null;
    }


    @GetMapping("/tourists")
    public List<User> getAllTourists() {
        return null;
    }

    @GetMapping("/drivers")
    public List<User> getAllDrivers() {
        return null;
    }

    /**
     * Simple health check endpoint
     */
    @GetMapping("/status")
    public String getStatus() {
        return null;
    }
}
