package com.umc10th.umc10th.global.apiPayload.exception;

import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import com.umc10th.umc10th.global.apiPayload.code.BaseErrorCode;
import com.umc10th.umc10th.global.apiPayload.code.GeneralErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;


}
