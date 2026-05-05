package com.umc10th.umc10th.domain.user.controller;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.store.dto.StoreReqDto;
import com.umc10th.umc10th.domain.store.dto.StoreResDto;
import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.exception.code.BaseSuccessCode;
import com.umc10th.umc10th.domain.user.service.UserService;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.QueryParameter;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me")
public class UserController {

    private final UserService userService;

    // 5. 마이페이지 화면
    @GetMapping("/{userId}/my-page")
    public ApiResponse<UserResDto.GetInfo> getMyPage(
            @PathVariable Long userId
    ) {

        //요청 dto변환
        UserReqDto.getInfo request = new UserReqDto.getInfo(userId);

        UserResDto.GetInfo result = userService.getInfo(request);
        return ApiResponse.onSuccess(BaseSuccessCode.OK, result);
    }

    // 어차피 나중에 추가 될 것들
    // 7. 내 정보 수정 (PATCH /api/users/me)
    @PatchMapping("")
    public ApiResponse<UserResDto.UpdateResultDto> updateMyInfo(
            @RequestBody UserReqDto.updateProfileRequestDto request) {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    // 8. 내 포인트 상세 조회
    @GetMapping("/points")
    public ApiResponse<UserResDto.PointDetailDto> getMyPoints() {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }
}
