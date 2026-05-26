package com.example.umc10th.mission.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.mission.dto.MissionReqDTO;
import com.example.umc10th.mission.dto.MissionResDTO;
import com.example.umc10th.mission.service.MissionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/users/{userId}/missions")
    public ApiResponse<List<MissionResDTO.UserMissionItem>> getUserMissions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "PROCEEDING") String status
    ) {
        List<MissionResDTO.UserMissionItem> result = missionService.getUserMissions(userId, status);
        return ApiResponse.onSuccess("MISSION200", "미션 목록 조회 성공", result);
    }

    @PostMapping("/users/missions/proceeding")
    public ApiResponse<MissionResDTO.ProceedingMissionPage> getProceedingMissions(
            @RequestBody @Valid MissionReqDTO.ProceedingMissionRequest request
    ) {
        MissionResDTO.ProceedingMissionPage result = missionService.getProceedingMissions(request);
        return ApiResponse.onSuccess("MISSION200", "진행중인 미션 조회 성공", result);
    }

    @PatchMapping("/users/{userId}/missions/{missionId}/complete")
    public ApiResponse<MissionResDTO.MissionCompleteResponse> completeMission(
            @PathVariable Long userId,
            @PathVariable Long missionId,
            @RequestBody(required = false) MissionReqDTO.MissionCompleteRequest request
    ) {
        MissionResDTO.MissionCompleteResponse result = missionService.completeMission(userId, missionId, request);
        return ApiResponse.onSuccess("MISSION200", "미션 완료 처리 성공", result);
    }
}
