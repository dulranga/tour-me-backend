package com.tourme.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tourme.models.Tourist;
import com.tourme.models.User;
import com.tourme.repositories.UserRepository;

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
}
