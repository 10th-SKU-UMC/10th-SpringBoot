package com.umc10th.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResDto {

    @Builder
    public record ReviewResultDto(
            Long reviewId,
            LocalDateTime createdAt
    ) {}
}
