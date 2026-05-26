package com.example.umc10th.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewReqDTO {

    public record CreateReviewRequest(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,

            @NotNull(message = "미션 ID는 필수입니다.")
            Long missionId,

            @NotBlank(message = "리뷰 내용은 필수입니다.")
            @Size(max = 500, message = "리뷰 내용은 500자 이하여야 합니다.")
            String content,

            @NotNull(message = "별점은 필수입니다.")
            @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
            @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
            Integer rating
    ) {}
}
