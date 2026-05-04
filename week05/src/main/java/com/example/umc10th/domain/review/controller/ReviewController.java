package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review API", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    // TODO: ReviewService 주입 (다음 주차)
    // private final ReviewService reviewService;

    @Operation(summary = "가게 리뷰 목록 조회")
    @GetMapping("/api/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewListDTO> getReviews(
            @Parameter(description = "가게 ID") @PathVariable Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // TODO: return ApiResponse.onSuccess(SuccessCode.REVIEW_FOUND, reviewService.getReviews(storeId, page, size));
        return ApiResponse.onSuccess(SuccessCode.REVIEW_FOUND, null);
    }

    @Operation(summary = "리뷰 작성")
    @PostMapping("/api/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewCreateResultDTO> createReview(
            @Parameter(description = "가게 ID") @PathVariable Long storeId,
            @RequestBody @Valid ReviewReqDTO.CreateReviewDTO request) {
        // TODO: return ApiResponse.onSuccess(SuccessCode.REVIEW_CREATED, reviewService.createReview(currentMemberId, storeId, request));
        return ApiResponse.onSuccess(SuccessCode.REVIEW_CREATED, null);
    }

    @Operation(summary = "리뷰 수정")
    @PatchMapping("/api/reviews/{reviewId}")
    public ApiResponse<ReviewResDTO.ReviewDTO> updateReview(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @RequestBody @Valid ReviewReqDTO.UpdateReviewDTO request) {
        // TODO: return ApiResponse.onSuccess(SuccessCode.REVIEW_UPDATED, reviewService.updateReview(currentMemberId, reviewId, request));
        return ApiResponse.onSuccess(SuccessCode.REVIEW_UPDATED, null);
    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/api/reviews/{reviewId}")
    public ApiResponse<Void> deleteReview(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId) {
        // TODO: reviewService.deleteReview(currentMemberId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.REVIEW_DELETED);
    }

    @Operation(summary = "리뷰 답글 작성")
    @PostMapping("/api/reviews/{reviewId}/replies")
    public ApiResponse<ReviewResDTO.ReviewReplyCreateResultDTO> createReply(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @RequestBody @Valid ReviewReqDTO.CreateReplyDTO request) {
        // TODO: return ApiResponse.onSuccess(SuccessCode.CREATED, reviewService.createReply(currentMemberId, reviewId, request));
        return ApiResponse.onSuccess(SuccessCode.CREATED, null);
    }

    @Operation(summary = "리뷰 답글 삭제")
    @DeleteMapping("/api/reviews/{reviewId}/replies/{replyId}")
    public ApiResponse<Void> deleteReply(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @Parameter(description = "답글 ID") @PathVariable Long replyId) {
        // TODO: reviewService.deleteReply(currentMemberId, reviewId, replyId);
        return ApiResponse.onSuccess(SuccessCode.OK);
    }
}
