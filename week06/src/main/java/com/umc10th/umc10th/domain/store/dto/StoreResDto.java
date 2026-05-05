package com.umc10th.umc10th.domain.store.dto;

import com.umc10th.umc10th.domain.mission.dto.MissionReqDto;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StoreResDto {

    // 1. 가게 상세 정보 (기존 유지)
    @Builder
    public record StoreDetailDto(
            Long storeId,
            String name,
            String address,
            Float score
    ) {}

    // 2. 리뷰 작성 결과 응답 (리뷰 작성 API용)
    @Builder
    public record ReviewResultDto(
            Long reviewId,
            LocalDateTime createdAt
    ) {}

    // 3. 가게 리뷰 목록 조회를 위한 DTO들
    @Builder
    public record ReviewListDto(
            List<ReviewDetailDto> reviewList,
            Integer listSize,      // 현재 페이지의 리뷰 개수
            Integer totalPage,     // 전체 페이지 수 (페이징 고려 시)
            Long totalElements,    // 전체 리뷰 개수
            Boolean isFirst,       // 첫 페이지 여부
            Boolean isLast         // 마지막 페이지 여부
    ) {}

    //리뷰 작성 결과(디테일)
    @Builder
    public record ReviewDetailDto(

            Long reviewId,

            String ownerNickname,  // 작성자 닉네임
            Float score,           // 평점
            String body,           // 리뷰 본문
            LocalDate createdAt    // 작성일
    ) {}


    @Builder
    public record MissionListDto(
            List<MissionReqDto.MissionDetailDto> missionList,
            Integer listSize,       // 현재 페이지의 데이터 개수
            Integer totalPage,      // 전체 페이지 수
            Long totalElements,     // 전체 데이터 개수
            Boolean isFirst,        // 첫 페이지 여부
            Boolean isLast          // 마지막 페이지 여부
    ) {

    }
}
