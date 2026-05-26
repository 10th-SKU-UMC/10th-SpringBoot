package com.umc10th.umc10th.domain.user.dto;


import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;


public class UserResDto {


    // 1. 홈 화면 전용 응답
    @Builder
    public record HomeViewDto(
            String regionName,     // "안암동" (사용자의 현재 지역 또는 관심 지역)
            int totalPoint,        // 999,999 P
            MissionResDto.MissionListDto missionList // 페이징 정보가 포함된 객체 MyMissionList
    ) {}

    @Builder
    public record ResponseBody(
            String stringTest,
            Long longTest
    ) {}

    @Builder
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            int point
    ) {}


    @Builder
    public record SignUpResponseDto(
            Long userId,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record UpdateResultDto(
            Long userId,
            LocalDateTime updatedAt
    ) {}

    // 로그인 결과
    @Builder
    public record LoginResultDto(
            String accessToken,
            Long userId
    ) {}

    // 포인트 상세
    @Builder
    public record PointDetailDto(
            Integer totalPoint,
            List<PointHistoryDto> history
    ) {}

    @Builder
    public record PointHistoryDto(
            String description,
            Integer amount,
            LocalDateTime createdAt
    ) {}





}
