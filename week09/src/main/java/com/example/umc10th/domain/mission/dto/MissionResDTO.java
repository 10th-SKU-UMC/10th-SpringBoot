package com.example.umc10th.domain.mission.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MissionResDTO {
    
    // 미션 목록 응답
    public record ShowMissionResDTO(
            Long missionId,
            Integer point,
            LocalDate deadline,
            Long storeId
    ){}

    // 미션 목록 응답(리스트)
    public record ShowMissionListResDTO(
            List<ShowMissionResDTO> missionList
    ){}

    // 미션 생성 응답
    public record CreateMissionResDTO(
    ){}

    // 가게 내 미션 조회 응답
    @Builder
    public record GetMissionResDTO(
            Long missionId,
            Integer point,
            String conditional
    ){}

    // 페이지네이션 툴
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}

    // 나의 미션 목록 응답
    @Builder
    public record GetMemberMissionResDTO(
            Long memberMissionId,
            String storeName,
            String conditional,
            Integer point
    ){}
}
