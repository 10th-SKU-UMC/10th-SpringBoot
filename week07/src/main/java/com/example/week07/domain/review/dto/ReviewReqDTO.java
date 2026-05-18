package com.example.week07.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewReqDTO {

    // 리뷰 작성 요청 데이터
    public record CreateReview(
            @NotNull(message = "별점은 필수입니다.")
            @Min(value = 1, message = "별점은 최소 1점입니다.")
            @Max(value = 5, message = "별점은 최대 5점입니다.")
            Integer rating,  // 별점 (1~5)

            @NotBlank(message = "리뷰 내용은 필수입니다.")
            @Size(max = 500, message = "리뷰 내용은 500자 이내로 작성해주세요.")
            String content,  // 리뷰 내용

            String photoUrl  // 리뷰 사진 URL (없으면 null)
    ) {}
}
