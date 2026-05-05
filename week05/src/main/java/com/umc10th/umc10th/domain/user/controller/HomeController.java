package com.umc10th.umc10th.domain.user.controller;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.exception.code.BaseSuccessCode;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/home")
public class HomeController {
    // 1. 홈 화면 (명세서: GET /api/users/me/home)
    @GetMapping("")
    public ApiResponse<UserResDto.HomeViewDto> getHome() {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }
}
