package com.example.umc10th.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewReqDTO {

    @Getter
    public static class CreateReviewDTO {
        @NotNull(message = "별점은 필수입니다.")
        @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
        @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
        private Integer rating;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        @Size(max = 500, message = "리뷰는 500자 이하로 작성해주세요.")
        private String content;

        private List<String> images;
    }

    @Getter
    public static class UpdateReviewDTO {
        @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
        @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
        private Integer rating;

        @Size(max = 500, message = "리뷰는 500자 이하로 작성해주세요.")
        private String content;
    }

    @Getter
    public static class CreateReplyDTO {
        @NotBlank(message = "답글 내용은 필수입니다.")
        @Size(max = 300, message = "답글은 300자 이하로 작성해주세요.")
        private String content;
    }
}
