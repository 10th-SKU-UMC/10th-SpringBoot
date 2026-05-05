package com.example.umc10th.review.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.review.dto.ReviewReqDTO;
import com.example.umc10th.review.dto.ReviewResDTO;
import com.example.umc10th.review.service.ReviewService;
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
            @RequestBody ReviewReqDTO.CreateReviewRequest request
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
}
