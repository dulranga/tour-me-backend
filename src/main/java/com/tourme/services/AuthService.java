package com.tourme.services;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tourme.exceptions.UserNotFoundException;
import com.tourme.models.User;
import com.tourme.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh.expiration:604800000}")
    private long refreshTokenExpiration; // 7 days by default

    public User login(String email, String password) throws UserNotFoundException {

        User u = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        if (u == null) {
            throw new UserNotFoundException();
        }

        boolean authenticated = passwordEncoder.matches(password, u.getPasswordHash());

        if (authenticated) {
            return u;
        }
        throw new UserNotFoundException();
    }

    public String generateToken(User u) {

        // Generate JWT token
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String token = Jwts.builder()
                .subject(String.valueOf(u.getUserId()))
                .claim("email", u.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();

        return token;
    }

    public String generateRefreshToken(User u) {
        // Generate refresh token with longer expiration
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String refreshToken = Jwts.builder()
                .subject(String.valueOf(u.getUserId()))
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key)
                .compact();

        return refreshToken;
    }

    public String generateTokenFromRefreshToken(String refreshToken) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            var jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(refreshToken);

            String userId = jws.getPayload().getSubject();
            User user = userRepository.findById(Integer.parseInt(userId))
                    .orElseThrow(() -> new UserNotFoundException());

            return generateToken(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired refresh token", e);
        }
    }

    public User getUserFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            var jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            String userId = jws.getPayload().getSubject();
            User user = userRepository.findById(Integer.parseInt(userId))
                    .orElseThrow(() -> new UserNotFoundException());

            return user;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }
}