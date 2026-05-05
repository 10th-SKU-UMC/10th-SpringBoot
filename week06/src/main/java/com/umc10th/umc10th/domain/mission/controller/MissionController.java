package com.umc10th.umc10th.domain.mission.controller;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import com.umc10th.umc10th.domain.mission.service.MissionService;
import com.umc10th.umc10th.domain.user.exception.code.BaseSuccessCode;
import com.umc10th.umc10th.domain.user.service.UserService;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/missions")
public class MissionController {

    private final MissionService missionService;
    private final UserService userService;


    //[미션 화면]
    //GET api/users/me/missions (쿼리 파라미터 없음)
    @GetMapping(value = "", params = "!status")
    public ApiResponse<MissionResDto.MissionViewDto> getMissionView() {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }


    //[미션 조회(진행중/완료)]
    @GetMapping(value = "/{userId}", params = "status")
    public ApiResponse<MissionResDto.MissionListDto> getMyMission(
            @PathVariable Long userId,
            @RequestParam(name = "missionStatus") MissionStatus missionStatus,
            @RequestParam(name = "page") Integer page // 페이지 번호
    ) {

        MissionResDto.MissionListDto result = missionService.getMyMissionList(userId, missionStatus, page);

        return ApiResponse.onSuccess(BaseSuccessCode.OK, result);
    }

    //미션 성공 누르기
    // 결과 URL: PATCH /api/users/me/missions/{missionId}/success
    @PatchMapping("/{missionId}/success")
    public ApiResponse<MissionResDto.CompleteResultDto> completeMission(
            @PathVariable Long missionId) {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

}
