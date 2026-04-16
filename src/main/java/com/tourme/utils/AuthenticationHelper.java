package com.tourme.utils;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tourme.models.User;
import com.tourme.repositories.UserRepository;
import com.tourme.exceptions.UserNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Helper class for authentication-related operations
 * Provides utilities to validate JWT tokens, extract user information, and check authentication status
 */
@Component
public class AuthenticationHelper {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Validates a JWT token and checks if it's still valid (not expired)
     * 
     * @param token The JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }
            
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extracts the user ID from a valid JWT token
     * 
     * @param token The JWT token
     * @return The user ID as an integer, or null if token is invalid
     */
    public Integer extractUserIdFromToken(String token) {
        try {
            if (!isTokenValid(token)) {
                return null;
            }
            
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            
            String userId = jws.getPayload().getSubject();
            return Integer.parseInt(userId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extracts the email from a valid JWT token
     * 
     * @param token The JWT token
     * @return The email address, or null if token is invalid
     */
    public String extractEmailFromToken(String token) {
        try {
            if (!isTokenValid(token)) {
                return null;
            }
            
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            
            return jws.getPayload().get("email", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if a user is authenticated by validating their token and verifying they exist in the database
     * 
     * @param token The JWT token to validate
     * @return true if the user is authenticated, false otherwise
     */
    public boolean isUserAuthenticated(String token) {
        try {
            Integer userId = extractUserIdFromToken(token);
            if (userId == null) {
                return false;
            }
            
            // Check if user exists in database
            return userRepository.findById(userId).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the authenticated user from a token
     * 
     * @param token The JWT token
     * @return The User object if authenticated, null otherwise
     * @throws UserNotFoundException if the token is invalid or user doesn't exist
     */
    public User getAuthenticatedUser(String token) throws UserNotFoundException {
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            throw new UserNotFoundException();
        }
        
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * Checks if a token is a refresh token
     * 
     * @param token The token to check
     * @return true if the token is a refresh token, false otherwise
     */
    public boolean isRefreshToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            
            String type = jws.getPayload().get("type", String.class);
            return "refresh".equals(type);
        } catch (Exception e) {
            return false;
        }
    }
}
