package com.example.week06.domain.mission.controller;

import com.example.week06.domain.mission.dto.MissionResDTO;
import com.example.week06.domain.mission.exception.code.MissionSuccessCode;
import com.example.week06.domain.mission.service.MissionService;
import com.example.week06.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식으로 응답하는 컨트롤러
@RequiredArgsConstructor // missionService를 생성자로 자동 주입
public class MissionController {

    private final MissionService missionService;

    // 미션 목록 조회 (진행중 / 완료, 페이징)
    // GET /missions?memberId=1&status=ongoing&page=0&size=10
    @GetMapping("/missions")
    public ApiResponse<MissionResDTO.MissionListRes> getMissions(
            @RequestParam Long memberId,                        // 임시 - 나중에 JWT 토큰으로 대체 예정
            @RequestParam String status,                        // ongoing(진행중) 또는 completed(완료)
            @RequestParam(defaultValue = "0") int page,        // 페이지 번호 (0부터 시작)
            @RequestParam(defaultValue = "10") int size        // 페이지 크기 (기본값 10)
    ) {
        return ApiResponse.onSuccess(MissionSuccessCode.GET_MISSIONS_OK, missionService.getMissions(memberId, status, page, size));
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
