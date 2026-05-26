package com.example.umc10th.global.exception;

import com.example.umc10th.global.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외 (ProjectException 하위 모든 예외)
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Object>> handleProjectException(ProjectException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode.getCode(), errorCode.getMessage()));
    }

    // @Valid - @RequestBody 필드 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.onFailure("COMMON400", message.isEmpty() ? "잘못된 요청입니다." : message));
    }

    // @Validated - @PathVariable, @RequestParam 검증 실패
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(
            ConstraintViolationException e
    ) {
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.onFailure("COMMON400", message.isEmpty() ? "잘못된 요청입니다." : message));
    }

    // JSON 파싱 실패 (잘못된 타입, 누락된 필드 등)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.onFailure("COMMON400", "요청 본문을 읽을 수 없습니다. JSON 형식을 확인해주세요."));
    }

    // 필수 쿼리 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e
    ) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.onFailure("COMMON400", "필수 파라미터 '" + e.getParameterName() + "'가 누락되었습니다."));
    }

    // 쿼리 파라미터/경로 변수 타입 불일치
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e
    ) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.onFailure("COMMON400",
                        "'" + e.getName() + "' 파라미터의 타입이 올바르지 않습니다."));
    }

    // 그 외 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.onFailure("COMMON500", "서버 내부 오류가 발생했습니다."));
    }
}
