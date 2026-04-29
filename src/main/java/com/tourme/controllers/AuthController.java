package com.tourme.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourme.dto.ApiResponse;
import com.tourme.exceptions.UserNotFoundException;
import com.tourme.models.User;
import com.tourme.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Marks this class as a REST API controller
@RestController

// Base URL for all authentication endpoints    
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

    /*
     * Handle user login request
     * Validate user credentials and generate JWT tokens
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest,
            HttpServletResponse response) {
        try {

              // Extract email and password from request body
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");

            // Authenticate user
            User u = authService.login(email, password);

           // Generate access and refresh tokens
            String accessToken = authService.generateToken(u);
            String refreshToken = authService.generateRefreshToken(u);

            // Create and set access token cookie
            Cookie accessTokenCookie = new Cookie("Authorization", accessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true); // Only send over HTTPS
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(24 * 60 * 60); // 24 hours
            accessTokenCookie.setAttribute("SameSite", "Strict");
            response.addCookie(accessTokenCookie);

            // Create and set refresh token cookie
            Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            refreshTokenCookie.setAttribute("SameSite", "Strict");
            response.addCookie(refreshTokenCookie);

            // Create response data
            Map<String, String> responseData = new HashMap<>();
            responseData.put("userId", String.valueOf(u.getUserId()));
            responseData.put("role", String.valueOf(u.getRole()));
            responseData.put("accessToken", accessToken);
            responseData.put("refreshToken", refreshToken);

            // Return success response
            return ApiResponse.ok("Login successful", responseData);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound("User not found");
        } catch (Exception e) {
            // Print server error message
            System.err.println("Error during login: " + e.getMessage());
            
           // Return internal server error response 
            return ApiResponse.internalServerError("Internal server error");
        }
    }

    /*
     * authenticate the user by their JWT token and invalidate the token to log
     * the user out.
     *
     * @param logoutRequest - A map containing the user's JWT token. this is json
     * file which we convert to map and then pass it to service.
     * call the service to authenticate the user by their JWT token and invalidate
     * the token to log the user out.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletResponse response) {

        // Clear the access token cookie
        Cookie authCookie = new Cookie("Authorization", null);
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setPath("/");
        authCookie.setMaxAge(0); // Delete cookie
        response.addCookie(authCookie);

        // Clear the refresh token cookie
        Cookie refreshTokenCookie = new Cookie("RefreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // Delete cookie
        response.addCookie(refreshTokenCookie);

        return ApiResponse.ok("Logout successful", null);
    }

    /*
     * refresh the user's JWT token by validating the provided refresh token and
     * generating a new access token if the refresh token is valid.
     * 
     * @param refreshRequest - A map containing the user's refresh token. this is
     * json file which we convert to map and then pass it to service.
     * call the service to refresh the user's JWT token by validating the provided
     * refresh token and generating a new access token if the refresh token is
     * valid.
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> refreshRequest,
            HttpServletResponse response) {
        try {
            // Extract refresh token from request
            String refreshToken = refreshRequest.get("refreshToken");
            // Generate new access token
            String newAccessToken = authService.generateTokenFromRefreshToken(refreshToken);

            // Update the access token cookie with new token
            Cookie accessTokenCookie = new Cookie("Authorization", newAccessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(accessTokenCookie);

            // Prepare response data
            Map<String, String> responseData = new HashMap<>();
            responseData.put("accessToken", newAccessToken);

            // Return success response
            return ApiResponse.ok("Token refreshed successfully", responseData);
        } catch (Exception e) {
            // Return unauthorized response if token is invalid
            return ApiResponse.unauthorized("Invalid or expired refresh token");
        }
    }

    /*
     * Get the current authenticated user from the JWT token stored in the
     * Authorization cookie.
     * 
     * @param request - The HTTP request containing the Authorization cookie
     * 
     * @return The current user's information
     */

    /*
     * Get currently authenticated user details
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        try {
            String token = null;

            // Extract token from Authorization cookie
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("Authorization".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            // Check if token exists
            if (token == null) {
                return ApiResponse.unauthorized("No authentication token found");
            }

            // Get user from JWT token
            User currentUser = authService.getUserFromToken(token);
            return ApiResponse.ok("Current user retrieved successfully", currentUser);
            // Return current user details
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound("User not found");

            // Return user not found response
        } catch (Exception e) {
            return ApiResponse.unauthorized("Invalid or expired token");
        }
    }
}
