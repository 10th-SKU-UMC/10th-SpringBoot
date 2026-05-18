package com.example.week05.domain.review.dto;

public class ReviewReqDTO {

    // 리뷰 작성 요청 데이터
    public record CreateReview(
            Integer rating,  // 별점 (1~5)
            String content,  // 리뷰 내용
            String photoUrl  // 리뷰 사진 URL (없으면 null)
    ) {}
}
