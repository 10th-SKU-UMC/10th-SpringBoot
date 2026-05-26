package com.example.umc10th.review.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.review.dto.ReviewReqDTO;
import com.example.umc10th.review.dto.ReviewResDTO;
import com.example.umc10th.review.service.ReviewService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReviewResponse> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewReqDTO.CreateReviewRequest request
    ) {
        ReviewResDTO.CreateReviewResponse result = reviewService.createReview(storeId, request);
        return ApiResponse.onSuccess("REVIEW201", "리뷰 작성 성공", result);
    }

    @GetMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewPageResponse> getReviewsByStore(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ReviewResDTO.ReviewPageResponse result = reviewService.getReviewsByStore(storeId, page, size);
        return ApiResponse.onSuccess("REVIEW200", "리뷰 목록 조회 성공", result);
    }

    /**
     * 내가 생성한 리뷰 조회 - 커서 기반 페이지네이션
     * sortBy=ID   : idCursor(마지막으로 받은 reviewId) 기준으로 다음 페이지
     * sortBy=RATING : starCursor + idCursor(동점 타이브레이크) 기준으로 다음 페이지
     */
    @GetMapping("/users/{userId}/reviews")
    public ApiResponse<ReviewResDTO.MyReviewCursorResponse> getMyReviews(
            @PathVariable Long userId,
            @RequestParam(required = false) Long idCursor,
            @RequestParam(required = false) BigDecimal starCursor,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") String sortBy
    ) {
        ReviewResDTO.MyReviewCursorResponse result = "RATING".equalsIgnoreCase(sortBy)
                ? reviewService.getMyReviewsByRating(userId, starCursor, idCursor, size)
                : reviewService.getMyReviewsById(userId, idCursor, size);

        return ApiResponse.onSuccess("REVIEW200", "내 리뷰 목록 조회 성공", result);
    }
}
