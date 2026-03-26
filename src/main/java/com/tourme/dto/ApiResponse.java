package com.tourme.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Standard API Response wrapper for all endpoints
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private HttpStatus status;

    public ApiResponse() {
        this.success = true;
        this.status = HttpStatus.OK;
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
        this.status = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }

    // Static builder methods - returning ResponseEntity directly
    public static <T> ResponseEntity<?> ok(String message, T data) {
        return new ResponseEntity<>(new ApiResponse<>(true, message, data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<?> created(String message, T data) {
        return new ResponseEntity<>(new ApiResponse<>(true, message, data), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<?> accepted(String message, T data) {
        return new ResponseEntity<>(new ApiResponse<>(true, message, data), HttpStatus.ACCEPTED);
    }

    public static <T> ResponseEntity<?> badRequest(String message) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<?> badRequest(String message, T data) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, data), HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<?> notFound(String message) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<?> unauthorized(String message) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<?> forbidden(String message) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<?> internalServerError(String message) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<?> status(boolean success, String message, T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiResponse<>(success, message, data), httpStatus);
    }
}
