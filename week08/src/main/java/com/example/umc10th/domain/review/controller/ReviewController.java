package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.service.ReviewService;
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

    private final ReviewService reviewService;

    @Operation(summary = "가게 리뷰 목록 조회", description = "미션(홈) — 가게의 리뷰 목록을 페이징으로 조회합니다.")
    @GetMapping("/api/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewListDTO> getReviews(
            @Parameter(description = "가게 ID") @PathVariable Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(SuccessCode.REVIEW_FOUND, reviewService.getReviews(storeId, page, size));
    }

    @Operation(summary = "리뷰 작성")
    @PostMapping("/api/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewCreateResultDTO> createReview(
            @Parameter(description = "가게 ID") @PathVariable Long storeId,
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid ReviewReqDTO.CreateReviewDTO request) {
        return ApiResponse.onSuccess(SuccessCode.REVIEW_CREATED,
                reviewService.createReview(memberId, storeId, request));
    }

    @Operation(summary = "리뷰 수정")
    @PatchMapping("/api/reviews/{reviewId}")
    public ApiResponse<ReviewResDTO.ReviewDTO> updateReview(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid ReviewReqDTO.UpdateReviewDTO request) {
        return ApiResponse.onSuccess(SuccessCode.REVIEW_UPDATED,
                reviewService.updateReview(memberId, reviewId, request));
    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/api/reviews/{reviewId}")
    public ApiResponse<Void> deleteReview(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId) {
        reviewService.deleteReview(memberId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.REVIEW_DELETED);
    }

    @Operation(summary = "내 리뷰 목록 조회", description = "마이페이지 — 작성한 리뷰 목록을 페이징으로 조회합니다.")
    @GetMapping("/api/users/me/reviews")
    public ApiResponse<ReviewResDTO.ReviewListDTO> getMyReviews(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(SuccessCode.REVIEW_FOUND,
                reviewService.getMyReviews(memberId, page, size));
    }

    @Operation(
            summary = "내가 생성한 리뷰 조회 (커서 기반)",
            description = "마이페이지 — 작성한 리뷰 목록을 커서 기반 페이지네이션으로 조회합니다. (사진 제외)\n\n" +
                    "sortBy: id(기본값, ID 내림차순) | rating(별점 내림차순, 동점은 ID 내림차순)\n\n" +
                    "cursor: 이전 응답의 nextCursor 값 (첫 페이지는 생략)"
    )
    @GetMapping("/api/users/me/reviews/cursor")
    public ApiResponse<ReviewResDTO.ReviewCursorListDTO> getMyReviewsCursor(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @Parameter(description = "커서 (이전 페이지의 nextCursor 값, 첫 페이지는 생략)")
            @RequestParam(required = false) String cursor,
            @Parameter(description = "페이지 크기 (기본값 10)")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "정렬 기준: id(기본값) | rating")
            @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.onSuccess(SuccessCode.REVIEW_FOUND,
                reviewService.getMyReviewsCursor(memberId, cursor, size, sortBy));
    }

    @Operation(summary = "리뷰 답글 작성")
    @PostMapping("/api/reviews/{reviewId}/replies")
    public ApiResponse<ReviewResDTO.ReviewReplyCreateResultDTO> createReply(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid ReviewReqDTO.CreateReplyDTO request) {
        return ApiResponse.onSuccess(SuccessCode.CREATED,
                reviewService.createReply(memberId, reviewId, request));
    }

    @Operation(summary = "리뷰 답글 삭제")
    @DeleteMapping("/api/reviews/{reviewId}/replies/{replyId}")
    public ApiResponse<Void> deleteReply(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @Parameter(description = "답글 ID") @PathVariable Long replyId,
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId) {
        reviewService.deleteReply(memberId, reviewId, replyId);
        return ApiResponse.onSuccess(SuccessCode.OK);
    }
}
