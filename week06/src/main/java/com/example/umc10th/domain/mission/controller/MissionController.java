package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MissionController {

    private final MissionService missionService;

    // 미션 목록
    @PostMapping("/v1/missions?location_id")
    public ApiResponse<MissionResDTO.ShowMissionListResDTO> showMissions(
            @RequestParam(name = "location_id") Long locationId // 쿼리 파라미터 검색
    ){
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }

    // 나의 미션 목록 조회
    @PostMapping("/v1/user-missions?is_complete=0")
    public ApiResponse<MissionResDTO.ShowMissionListResDTO> getUserMissions(
            @RequestParam(name = "is_complete") Integer isComplete // isComplete 파라미터로 검색(진향중:0, 완료:1)
    ){
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }

    // 미션 성공 누르기
    @PostMapping("/v1/user-missions/{user_mission_id}/is_complete")
    public ApiResponse<Void> completeMission(
            @PathVariable(name = "userMissionId") Long userMissionId
    ){
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }
}
