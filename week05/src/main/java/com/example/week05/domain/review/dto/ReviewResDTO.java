package com.example.week05.domain.review.dto;

import lombok.Builder;

import java.util.List;

public class ReviewResDTO {

    // 리뷰 하나의 정보
    @Builder
    public record ReviewInfo(
            Long reviewId,     // 리뷰 ID
            String storeName,  // 가게 이름
            Integer rating,    // 별점
            String content,    // 리뷰 내용
            String photoUrl,   // 리뷰 사진 URL
            String createdAt   // 작성일
    ) {}

    // 리뷰 작성 응답 데이터
    @Builder
    public record CreateReviewRes(
            Long reviewId // 생성된 리뷰 ID
    ) {}

    // 리뷰 목록 응답 데이터
    @Builder
    public record ReviewListRes(
            List<ReviewInfo> reviews, // 리뷰 목록
            Integer totalCount        // 전체 리뷰 수
    ) {}
}
