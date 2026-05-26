package com.umc10th.umc10th.domain.mission.controller;

import com.umc10th.umc10th.domain.mission.dto.MissionReqDto;
import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import com.umc10th.umc10th.domain.mission.enums.MissionSuccessCode;
import com.umc10th.umc10th.domain.mission.service.MissionService;
import com.umc10th.umc10th.domain.store.dto.StoreResDto;
import com.umc10th.umc10th.domain.user.service.UserService;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import com.umc10th.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/missions")
public class MissionController {

    private final MissionService missionService;
    private final UserService userService;


    //가게별 미션 생성
    @PostMapping("/stores/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDto.CreateMission dto
    ) {


        return ApiResponse.onSuccess(MissionSuccessCode.CREATED, missionService.createMission(storeId, dto));
    }

    //가게 별 미션 보기
    @GetMapping("/stores/{storeId}/missions")
    public ApiResponse<MissionResDto.Pagenation<MissionResDto.GetMission>> getStoreMissions(
            @PathVariable Long storeId,
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber,
            @RequestParam(required = false) String sort
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId, pageSize, pageNumber, sort));
    }

    // 진행중인 미션 조회 (오프셋 기반 페이지네이션, userId는 Request Body)
    @GetMapping("/challenging")
    public ApiResponse<MissionResDto.Pagenation<MissionResDto.GetMyChallengingDto>> getMyChallengingMissions(
            @RequestBody MissionReqDto.GetMyMissions request)
    {
        return ApiResponse.onSuccess(MissionSuccessCode.OK,
                missionService.getMyChallengingMissions(request));
    }

    //[미션 화면]
    //GET api/users/me/missions (쿼리 파라미터 없음)
    @GetMapping(value = "", params = "!status")
    public ApiResponse<MissionResDto.MissionViewDto> getMissionView() {
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }


    //[미션 조회(진행중/완료)]
    @GetMapping(value = "/{userId}", params = "status")
    public ApiResponse<MissionResDto.MissionListDto> getMyMission(
            @PathVariable Long userId,
            @RequestParam(name = "missionStatus") MissionStatus missionStatus,
            @RequestParam(name = "page") Integer page // 페이지 번호
    ) {

        MissionResDto.MissionListDto result = missionService.getMyMissionList(userId, missionStatus, page);

        return ApiResponse.onSuccess(MissionSuccessCode.OK, result);
    }

    //미션 성공 누르기
    // 결과 URL: PATCH /api/users/me/missions/{missionId}/success
    @PatchMapping("/{missionId}/success")
    public ApiResponse<MissionResDto.CompleteResultDto> completeMission(
            @PathVariable Long missionId) {
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }

}
