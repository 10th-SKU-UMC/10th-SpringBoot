package com.umc10th.umc10th.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewReqDto {

    // 리뷰 관련
    public record ReviewCreateDto(
            @NotBlank String title,
            @NotNull Float score,
            @NotBlank String body
    ) {}
}
