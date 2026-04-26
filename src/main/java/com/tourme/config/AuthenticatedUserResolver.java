package com.tourme.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.tourme.annotations.AuthenticatedUser;
import com.tourme.utils.AuthenticationHelper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Custom Spring argument resolver that extracts the authenticated user's ID
 * from the JWT token
 * in the Authorization cookie and injects it into method parameters marked
 * with @AuthenticatedUser.
 * 
 * This resolver:
 * 1. Checks if the method parameter is annotated with @AuthenticatedUser
 * 2. Extracts the JWT token from the "Authorization" cookie
 * 3. Uses AuthenticationHelper to validate and extract the user ID from the
 * token
 * 4. Throws AuthenticationException if the token is missing or invalid
 */
@Component
public class AuthenticatedUserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private AuthenticationHelper authenticationHelper;

    /**
     * Checks if this resolver supports the given method parameter.
     * Returns true only if the parameter is annotated with @AuthenticatedUser and
     * is an integer.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUser.class)
                && parameter.getParameterType().equals(Integer.TYPE);
    }

    /**
     * Resolves the authenticated user's ID from the JWT token in the Authorization
     * cookie.
     * 
     * @return The user ID as an Integer
     * @throws IllegalArgumentException if the token is missing or invalid
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalArgumentException("HTTP request required");
        }

        // Extract JWT token from Authorization cookie
        String token = extractTokenFromCookies(request);

        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Authorization token not found");
        }

        // Validate token and extract user ID
        Integer userId = authenticationHelper.extractUserIdFromToken(token);

        if (userId == null) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        return userId;
    }

    /**
     * Extracts the JWT token from the "Authorization" cookie in the request.
     * 
     * @param request The HTTP request
     * @return The token string, or null if not found
     */
    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("Authorization".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
