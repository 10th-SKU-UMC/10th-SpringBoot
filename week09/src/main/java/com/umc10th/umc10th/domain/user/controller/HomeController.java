package com.umc10th.umc10th.domain.user.controller;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.exception.code.UserSuccessCode;
import com.umc10th.umc10th.domain.user.service.UserService;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import com.umc10th.umc10th.global.sequrity.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    // 1. 홈 화면 (명세서: GET /api/users/me/home)
    @GetMapping("/api/users/me/home")
    public ApiResponse<UserResDto.HomeViewDto> getHome(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "regionName") String region_name,
            @RequestParam(name = "page") Integer page
    ) {
        UserReqDto.getHomeView request = new UserReqDto.getHomeView(authUser.getUser().getId(), region_name, page);
        UserResDto.HomeViewDto result = userService.getHomeView(request);

        return ApiResponse.onSuccess(UserSuccessCode.OK, result);
    }
}
