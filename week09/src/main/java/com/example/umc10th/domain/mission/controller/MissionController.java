package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MissionController {

    private final MissionService missionService;

    // 미션 목록
    @GetMapping("/v1/missions?location_id")
    public ApiResponse<MissionResDTO.ShowMissionListResDTO> showMissions(
            @RequestParam(name = "location_id") Long locationId // 쿼리 파라미터 검색
    ){
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }

    // 나의 미션 목록 조회 1
    @GetMapping("/v1/user-missions?is_complete=0")
    public ApiResponse<MissionResDTO.ShowMissionListResDTO> getUserMissions(
            @RequestParam(name = "is_complete") Integer isComplete // isComplete 파라미터로 검색(진향중:0, 완료:1)
    ){
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }

    // 나의 미션 목록 조회 2 페이지
    @PostMapping("v1/user-mission/list")
    public ApiResponse<Page<MissionResDTO.GetMemberMissionResDTO>> getMemberMissionList(
            @RequestBody @Valid MissionReqDTO.ShowMemberMissionReqDTO request,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ){
        Page<MemberMission> memberMissionPage = missionService.getMemberMissionList(request, page, size);

        Page<MissionResDTO.GetMemberMissionResDTO> resultPage = memberMissionPage
                .map(MissionConverter::toGetMemberMission);
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, resultPage);
    }

    // 미션 성공 누르기
    @PostMapping("/v1/user-missions/{user_mission_id}/is_complete")
    public ApiResponse<Void> completeMission(
            @PathVariable(name = "userMissionId") Long userMissionId
    ){
        return ApiResponse.onSuccess(MissionSuccessCode.OK, null);
    }

    // 가게 내 미션 조회하기
    @GetMapping("/v1/store/{storeId}/mission")
    public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMissionResDTO>> getStoreMission(
            // List -> Page 변경
            @PathVariable long storeId,
            @RequestParam Integer pageSize,
            //@RequestParam Integer pageNumber,
            @RequestParam String cursor,
            @RequestParam String query
            //@RequestParam(required = false) String sort
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getStoreMission(storeId, pageSize, cursor, query));
    }

    // 가게 내 미션 생성하기
    @PostMapping("/v1/store/{storeId}/mission")
    public ApiResponse<Void> createMission(
            @PathVariable long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMissionReqDTO dto
    ){
        BaseSuccessCode code = MissionSuccessCode.CREATE;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }
}
