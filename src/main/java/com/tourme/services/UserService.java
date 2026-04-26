package com.tourme.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tourme.models.Administrator;
import com.tourme.models.Driver;
import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.UserRepository;
import com.tourme.exceptions.UserNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User getUserById(int Id) {
        User u = userRepository.findById(Id).orElse(null);
        return u;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Tourist registerTourist(Tourist tourist) {
        String password = tourist.getPasswordHash();
        tourist.setPasswordHash(passwordEncoder.encode(password));

        return userRepository.save(tourist);
    }

    public Driver registerDriver(Driver driver) {
        String password = driver.getPasswordHash();
        driver.setPasswordHash(passwordEncoder.encode(password));
        System.out.println(password);
        System.out.println(passwordEncoder.encode(password));
        return userRepository.save(driver);
    }

    public Administrator registerAdmin(Administrator admin) {
        String password = admin.getPasswordHash();
        admin.setPasswordHash(passwordEncoder.encode(password));

        return userRepository.save(admin);
    }

    public User updateProfile(int id, User profileData) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        user.setName(profileData.getName());
        user.setEmail(profileData.getEmail());

        return userRepository.save(user);
    }

    public Driver updateVehicleDetails(int id, String vehicleDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        if (!(user instanceof Driver)) {
            throw new IllegalArgumentException("User is not a driver");
        }

        Driver driver = (Driver) user;
        driver.setVehicleDetails(vehicleDetails);
        return userRepository.save(driver);
    }

    public List<User> getAllTourists() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Tourist)
                .toList();
    }

    public List<User> getAllDrivers() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Driver)
                .toList();
    }
}
