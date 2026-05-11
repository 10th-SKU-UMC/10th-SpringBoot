package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mission API", description = "미션 관련 API")
@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @Operation(
            summary = "홈 화면 미션 조회",
            description = "미션 달성 진행도(완료 수/목표)와 현재 진행 중인 MY MISSION 목록을 반환합니다."
    )
    @GetMapping("/home")
    public ApiResponse<MissionResDTO.HomeMissionDTO> getHomeMissions(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId) {
        return ApiResponse.onSuccess(SuccessCode.MISSION_FOUND, missionService.getHomeMissions(memberId));
    }

    @Operation(
            summary = "내 미션 목록 조회",
            description = "상태(IN_PROGRESS=진행중 / COMPLETED=진행완료) 별 미션 목록을 페이징으로 반환합니다."
    )
    @GetMapping("/my")
    public ApiResponse<MissionResDTO.MissionListDTO> getMyMissions(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @Parameter(description = "미션 상태 (IN_PROGRESS | COMPLETED)")
            @RequestParam(defaultValue = "IN_PROGRESS") MissionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(SuccessCode.MISSION_FOUND,
                missionService.getMyMissions(memberId, status, page, size));
    }

    @Operation(summary = "미션 도전 시작", description = "선택한 미션에 도전을 시작합니다.")
    @PostMapping("/{missionId}/start")
    public ApiResponse<MissionResDTO.StartMissionResultDTO> startMission(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @Parameter(description = "미션 ID") @PathVariable Long missionId) {
        return ApiResponse.onSuccess(SuccessCode.MISSION_STARTED,
                missionService.startMission(memberId, missionId));
    }

    @Operation(summary = "미션 완료 처리", description = "진행 중인 미션을 완료 처리하고 포인트를 적립합니다.")
    @PatchMapping("/{memberMissionId}/complete")
    public ApiResponse<Void> completeMission(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @Parameter(description = "회원-미션 ID") @PathVariable Long memberMissionId) {
        missionService.completeMission(memberId, memberMissionId);
        return ApiResponse.onSuccess(SuccessCode.MISSION_COMPLETED);
    }
}
