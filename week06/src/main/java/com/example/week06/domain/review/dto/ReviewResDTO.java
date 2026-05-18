package com.example.week06.domain.review.dto;

import lombok.Builder;

import java.util.List;

public class ReviewResDTO {

    // 가게 리뷰 하나의 정보
    @Builder
    public record StoreReviewInfo(
            String memberName,          // 작성자 닉네임
            String createdAt,           // 작성일
            java.math.BigDecimal star,  // 별점
            String content              // 리뷰 내용
            // String photoUrl          // 리뷰 사진 URL (스킵)
    ) {}

    // 가게 리뷰 목록 응답 데이터 (페이징 포함)
    @Builder
    public record StoreReviewListRes(
            List<StoreReviewInfo> reviews, // 리뷰 목록
            Integer totalCount,            // 전체 리뷰 수
            Boolean hasNext                // 다음 페이지 존재 여부
    ) {}

    // 리뷰 작성 응답 데이터
    @Builder
    public record CreateReviewRes(
            Long reviewId // 생성된 리뷰 ID
    ) {}

    // 내 리뷰 목록 응답 데이터
    @Builder
    public record ReviewListRes(
            List<StoreReviewInfo> reviews, // 리뷰 목록
            Integer totalCount             // 전체 리뷰 수
    ) {}
}
