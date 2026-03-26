package com.tourme.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourme.dto.ApiResponse;
import com.tourme.exceptions.UserNotFoundException;
import com.tourme.models.User;
import com.tourme.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /*
     * check the credentials of the user and if they are valid, generate a JWT token
     * and return it in the response.
     * 
     * @param loginRequest - A map containing the user's email and password. this is
     * json file which we convert to map and then pass it to service.
     * call the service to check the credentials of the user and if they are valid,
     * generate a JWT token and return it in the response.
     */
    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");
            User u = authService.login(email, password);

            String token = authService.generateToken(u);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", String.valueOf(u.getUserId()));

            return ApiResponse.ok("Login successful", response);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound("User not found");
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return ApiResponse.internalServerError("Internal server error");
        }
    }

    // /*
    // * authenticate the user by their JWT token and invalidate the token to log
    // the
    // * user out.
    // *
    // * @param logoutRequest - A map containing the user's JWT token. this is json
    // * file which we convert to map and then pass it to service.
    // * call the service to authenticate the user by their JWT token and invalidate
    // * the token to log the user out.
    // */
    // @PostMapping("/logout")
    // public ResponseEntity<?> logout(@RequestBody Map<String, String>
    // logoutRequest) {
    // String token = logoutRequest.get("token");
    // authService.logout(token);
    // return ResponseEntity.ok().body("Logout successful");
    // }

    // /*
    // refresh the user's JWT token by validating the provided refresh token and
    // generating a new access token if the refresh token is valid.
    // * @param refreshRequest - A map containing the user's refresh token. this is
    // json file which we convert to map and then pass it to service.
    // * call the service to refresh the user's JWT token by validating the provided
    // refresh token and generating a new access token if the refresh token is
    // valid.
    // */
    // @PostMapping("/refresh-token")
    // public ResponseEntity<?> refreshToken(@RequestBody Map<String, String>
    // refreshRequest) {
    // String refreshToken = refreshRequest.get("refreshToken");
    // return authService.refreshToken(refreshToken);
    // }
}
