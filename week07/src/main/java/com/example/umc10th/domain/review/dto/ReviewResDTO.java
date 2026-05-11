package com.example.umc10th.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListDTO {
        private List<ReviewDTO> reviews;
        private Double avgRating;
        private Integer totalCount;
        private Integer currentPage;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDTO {
        private Long reviewId;
        private Integer rating;
        private String content;
        private List<String> imageUrls;
        private String memberNickname;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        private List<ReviewReplyDTO> replies;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewCreateResultDTO {
        private Long reviewId;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewReplyDTO {
        private Long replyId;
        private String content;
        private String memberNickname;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewReplyCreateResultDTO {
        private Long replyId;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

    /** 커서 기반 페이지네이션용 리뷰 항목 (사진 제외) */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewCursorItemDTO {
        private Long reviewId;
        private Integer rating;
        private String content;
        private String storeName;
        private String memberNickname;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

    /** 커서 기반 페이지네이션 응답 */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewCursorListDTO {
        private List<ReviewCursorItemDTO> reviews;
        private String nextCursor;   // null이면 마지막 페이지
        private boolean hasNext;
        private int size;
    }
}

