package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MissionReqDTO {

    // 미션 목록 요청
    public record ShowMissionListReqDTO(
    ){}

    // 미션 생성 요청
    public record CreateMissionReqDTO(
            @NotNull(message = "마감 기한은 필수입니다.")
            LocalDate deadline,
            @NotNull(message = "미션 포인트는 필수입니다.")
            Integer point,
            @NotBlank(message = "조건은 빈 칸일 수 없습니다.")
            String conditional
    ){}

    // 나의 미션 목록 요청
    public record ShowMemberMissionReqDTO(
            @NotNull(message = "검색을 위한 유저 아이디는 필수")
            Long userId,
            @NotNull(message = "진행/ 왼료 선택 필수")
            Integer isComplete
    ){}
}
