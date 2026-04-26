package com.tourme.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration to register custom argument resolvers with Spring MVC.
 * This registers the AuthenticatedUserResolver so that method parameters
 * marked with @AuthenticatedUser are automatically resolved.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticatedUserResolver authenticatedUserResolver;

    /**
     * Adds the custom AuthenticatedUserResolver to the list of argument resolvers.
     * This resolver will intercept parameters marked with @AuthenticatedUser
     * and inject the authenticated user's ID from the JWT token.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedUserResolver);
    }
}
