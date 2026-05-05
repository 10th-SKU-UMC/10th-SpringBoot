package com.umc10th.umc10th.domain.review.controller;

import com.umc10th.umc10th.domain.review.service.ReviewService;
import com.umc10th.umc10th.domain.store.dto.StoreReqDto;
import com.umc10th.umc10th.domain.store.dto.StoreResDto;
import com.umc10th.umc10th.domain.user.exception.code.BaseSuccessCode;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me") // 공통 경로 추가
public class ReviewController {

    private final ReviewService reviewService;

    //마이 페이지 리뷰 작성
    // 결과 URL: POST /api/users/me/missions/{missionId}/reviews
    @PostMapping("/missions/{missionId}/reviews")
    public ApiResponse<StoreResDto.ReviewResultDto> createReview(
            @PathVariable Long missionId,
            @RequestBody StoreReqDto.ReviewCreateDto request) {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    //내가 쓴 리뷰만 보기
    // 결과 URL: GET /api/users/me/reviews
    @GetMapping("/reviews")
    public ApiResponse<StoreResDto.ReviewListDto> getMyReviews() {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }
}
