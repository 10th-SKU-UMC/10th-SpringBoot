package com.example.week07.domain.review.dto;

import lombok.Builder;

import java.math.BigDecimal;
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

    // 내 리뷰 하나의 정보
    @Builder
    public record MyReviewInfo(
            Long reviewId,        // 리뷰 ID
            String storeName,     // 가게 이름
            BigDecimal star,      // 별점
            String content,       // 리뷰 내용
            String createdAt,     // 작성일
            String replyContent   // 사장님 답글 (없으면 null)
    ) {}

    // 내 리뷰 목록 응답 데이터 (커서 기반 페이지네이션)
    @Builder
    public record MyReviewListRes(
            List<MyReviewInfo> reviews, // 리뷰 목록
            Boolean hasNext,            // 다음 데이터 존재 여부
            String nextCursor,          // 다음 요청에 사용할 커서값
            Integer listSize            // 현재 응답에 담긴 리뷰 수
    ) {}
}
