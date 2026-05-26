package com.umc10th.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDto {

    @Builder
    public record ReviewResultDto(
            Long reviewId,
            LocalDateTime createdAt
    ) {}

    // 리뷰 1건 아이템
    @Builder
    public record GetMyReviewDto(
            Long reviewId,
            String storeName,
            Float score,
            String body,
            LocalDate createdAt,
            String ownerReply   // 사장님 답글, 없으면 null
    ) {}

    // 커서 기반 페이지네이션 래퍼
    @Builder
    public record Pagenation<T>(
            List<T> data,
            Long nextCursor,        // 다음 페이지 커서 (마지막 review_id)
            Float nextScoreCursor,  // 별점 정렬 시 다음 커서
            Boolean hasNext
    ) {}
}
