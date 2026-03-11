package com.tourme.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Object loginRequest) {
        // to be implemented: validate credentials, generate JWT token, etc.
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // to be implemented: validate credentials, generate JWT token, etc.
        return null;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String token) {
        return null;
    }
}
