package com.tourme.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourme.models.User;
import com.tourme.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(int Id) {
        User u = userRepository.findById(Id).orElse(null);
        return u;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
