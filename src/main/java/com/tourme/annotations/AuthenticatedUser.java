package com.tourme.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method parameter for automatic injection of the
 * authenticated user's ID.
 * The user ID is extracted from the JWT token in the Authorization cookie.
 * 
 * Usage:
 * 
 * @PostMapping
 *              public ResponseEntity<?> submitBid(@AuthenticatedUser int
 *              driverId, @RequestParam int itineraryId, ...) {
 *              // driverId is automatically extracted from the JWT token
 *              }
 * 
 *              If the token is missing or invalid, a 401 Unauthorized response
 *              is returned.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUser {
}
