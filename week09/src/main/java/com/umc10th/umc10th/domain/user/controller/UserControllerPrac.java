package com.umc10th.umc10th.domain.user.controller;

import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.exception.UserException;
import com.umc10th.umc10th.domain.user.exception.code.UserErrorCode;
import com.umc10th.umc10th.domain.user.service.UserService;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import com.umc10th.umc10th.global.apiPayload.code.GeneralErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserControllerPrac {

    private final UserService userService;


    @GetMapping("/test")
    public String test() {
        throw new UserException(UserErrorCode.USER_NOT_FOUND);
    }


//    @PostMapping("/query-parameter")
//    public String exception(@RequestParam String queryParameter) {
//        return userService.singleParameter(queryParameter);
//    }

    @PostMapping("/query-parameter")
    public ApiResponse<String> exception(@RequestParam String queryParameter) {
        GeneralErrorCode code = GeneralErrorCode.FORBIDDEN;
        return ApiResponse.onFailure(code, userService.singleParameter(queryParameter));
    }

    @PostMapping("/request-body")
    public UserResDto.ResponseBody requestBody(
            @RequestBody UserReqDto.RequestBody dto
            ) {
        return userService.requestBody(dto);
    }

    @PostMapping("/{pathVariable}")
    public String pathVariable(@PathVariable String pathVariable) {

        return userService.singleParameter(pathVariable);
    }

    @PostMapping("/header")
    public String header(
            @RequestHeader("test") String test
    ) {
        return userService.singleParameter(test);
    }

}
