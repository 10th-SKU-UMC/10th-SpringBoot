package com.example.umc10th.global.response;

public record ApiResponse<T>(
        Boolean isSuccess,
        String code,
        String message,
        T result
) {
    public static <T> ApiResponse<T> onSuccess(String code, String message, T result) {
        return new ApiResponse<>(true, code, message, result);
    }

    public static ApiResponse<Object> onFailure(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}

