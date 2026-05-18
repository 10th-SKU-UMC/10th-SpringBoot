package com.example.week05.domain.mission.controller;

import com.example.week05.domain.mission.dto.MissionResDTO;
import com.example.week05.domain.mission.exception.code.MissionSuccessCode;
import com.example.week05.domain.mission.service.MissionService;
import com.example.week05.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식으로 응답하는 컨트롤러
@RequiredArgsConstructor // missionService를 생성자로 자동 주입
public class MissionController {

    private final MissionService missionService;

    // 미션 목록 조회 (진행중 / 완료)
    // GET /missions?memberId=1&status=ongoing
    @GetMapping("/missions")
    public ApiResponse<MissionResDTO.MissionListRes> getMissions(
            @RequestParam Long memberId,  // 임시 - 나중에 JWT 토큰으로 대체 예정
            @RequestParam String status   // ongoing(진행중) 또는 completed(완료)
    ) {
        return ApiResponse.onSuccess(MissionSuccessCode.GET_MISSIONS_OK, missionService.getMissions(memberId, status));
    }

    // 미션 성공 처리
    // PATCH /missions/{missionId}/success?memberId=1
    @PatchMapping("/missions/{missionId}/success")
    public ApiResponse<MissionResDTO.MissionSuccessRes> completeMission(
            @RequestParam Long memberId,       // 임시 - 나중에 JWT 토큰으로 대체 예정
            @PathVariable Long missionId       // URL 경로에서 미션 ID 받기
    ) {
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_SUCCESS_OK, missionService.completeMission(memberId, missionId));
    }
}
