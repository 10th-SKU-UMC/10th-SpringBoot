package com.umc10th.umc10th.domain.mission.controller;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import com.umc10th.umc10th.domain.mission.service.MissionService;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.exception.code.BaseSuccessCode;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/missions")
public class MissionController {

    private final MissionService missionService;

    //미션 목록 조회
    @GetMapping("")
    public ApiResponse<MissionResDto.MissionViewDto> getMissionView() {
        // Service에서 요약 정보와 초기 리스트를 조합해서 반환
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    //미션 조회
    @GetMapping("")
    public ApiResponse<MissionResDto.MissionListDto> getMyMission(
            @RequestParam MissionStatus status
    ) {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    //미션 성공 누르기
    // 결과 URL: PATCH /api/users/me/missions/{missionId}/success
    @PatchMapping("/{missionId}/success")
    public ApiResponse<MissionResDto.CompleteResultDto> completeMission(
            @PathVariable Long missionId) {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

}
