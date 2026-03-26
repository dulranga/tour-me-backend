package com.tourme.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Standard API Response wrapper for all endpoints
 */
public class ApiResponse<T> extends ResponseEntity<ApiResponse<T>> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse() {
        super(HttpStatus.OK);
    }

    public ApiResponse(boolean success, String message, T data) {
        super(HttpStatus.OK);
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(boolean success, String message, T data, HttpStatus status) {
        super(status);
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(boolean success, String message) {
        super(HttpStatus.OK);
        this.success = success;
        this.message = message;
        this.data = null;
    }

    @Override
    public ApiResponse<T> getBody() {
        return this;
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

    // Static builder methods
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.CREATED);
    }

    public static <T> ApiResponse<T> accepted(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.ACCEPTED);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST);
    }

    public static <T> ApiResponse<T> badRequest(String message, T data) {
        return new ApiResponse<>(false, message, data, HttpStatus.BAD_REQUEST);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.NOT_FOUND);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.UNAUTHORIZED);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.FORBIDDEN);
    }

    public static <T> ApiResponse<T> internalServerError(String message) {
        return new ApiResponse<>(false, message, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ApiResponse<T> status(boolean success, String message, T data, HttpStatus status) {
        return new ApiResponse<>(success, message, data, status);
    }
}
