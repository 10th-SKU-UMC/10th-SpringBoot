package com.umc10th.umc10th.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewReqDto {

    // 내가 쓴 리뷰 조회 (커서 기반 페이지네이션)
    public record GetMyReviews(
            @NotNull Long userId,
            Long lastId,        // null이면 첫 페이지
            Float lastScore,    // 별점 정렬 시 사용, null이면 첫 페이지
            @NotNull String sortType,  // "ID" | "SCORE"
            @NotNull Integer size
    ) {}

    // 리뷰 관련
    public record ReviewCreateDto(
            @NotBlank String title,
            @NotNull Float score,
            @NotBlank String body
    ) {}
}
