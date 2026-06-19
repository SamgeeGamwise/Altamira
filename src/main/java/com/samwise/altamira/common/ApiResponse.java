package com.samwise.altamira.common;

import java.util.List;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        List<ApiError> errors,
        Meta meta
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "OK", data, null, Meta.of());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null, Meta.of());
    }

    public static ApiResponse<Void> error(String message, List<ApiError> errors) {
        return new ApiResponse<>(false, message, null, errors, Meta.of());
    }
}
