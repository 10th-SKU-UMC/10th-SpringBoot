package com.example.umc10th.global.apiPayload;
import com.example.umc10th.global.apiPayload.code.BaseCode;
import com.example.umc10th.global.apiPayload.code.ErrorCode;
import com.example.umc10th.global.apiPayload.code.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공 응답 (result 있음)
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, SuccessCode.OK.getCode(), SuccessCode.OK.getMessage(), result);
    }

    // 성공 응답 (커스텀 코드)
    public static <T> ApiResponse<T> onSuccess(SuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // 성공 응답 (result 없음)
    public static ApiResponse<Void> onSuccess(SuccessCode code) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> onFailure(ErrorCode code) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), null);
    }

    // 실패 응답 (커스텀 메시지)
    public static <T> ApiResponse<T> onFailure(ErrorCode code, String message) {
        return new ApiResponse<>(false, code.getCode(), message, null);
    }

    // BaseCode 기반 응답
    public static <T> ApiResponse<T> of(BaseCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }
}

