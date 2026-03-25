package com.tourme.controllers;

import com.tourme.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // /*
    // check the credentials of the user and if they are valid, generate a JWT token and return it in the response. 
    //  * @param loginRequest - A map containing the user's email and password. this is json file which we convert to map and then pass it to service.
    //  * call the service to check the credentials of the user and if they are valid, generate a JWT token and return it in the response.
    // */
    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
    //     String email = loginRequest.get("email");
    //     String password = loginRequest.get("password");
    //     return authService.login(email, password);
    // }

    // /*
    // authenticate the user by their JWT token and invalidate the token to log the user out. 
    //  * @param logoutRequest - A map containing the user's JWT token. this is json file which we convert to map and then pass it to service.
    //  * call the service to authenticate the user by their JWT token and invalidate the token to log the user out.
    // */
    // @PostMapping("/logout")
    // public ResponseEntity<?> logout(@RequestBody Map<String, String> logoutRequest) {
    //     String token = logoutRequest.get("token");
    //     return authService.logout(token);
    // }

    // /*
    // refresh the user's JWT token by validating the provided refresh token and generating a new access token if the refresh token is valid. 
    //  * @param refreshRequest - A map containing the user's refresh token. this is json file which we convert to map and then pass it to service.
    //  * call the service to refresh the user's JWT token by validating the provided refresh token and generating a new access token if the refresh token is valid.
    // */
    // @PostMapping("/refresh-token")
    // public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> refreshRequest) {
    //     String refreshToken = refreshRequest.get("refreshToken");
    //     return authService.refreshToken(refreshToken);
    // }
}
