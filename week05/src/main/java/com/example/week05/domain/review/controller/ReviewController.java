package com.example.week05.domain.review.controller;

import com.example.week05.domain.review.dto.ReviewReqDTO;
import com.example.week05.domain.review.dto.ReviewResDTO;
import com.example.week05.domain.review.exception.code.ReviewSuccessCode;
import com.example.week05.domain.review.service.ReviewService;
import com.example.week05.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식으로 응답하는 컨트롤러
@RequiredArgsConstructor // reviewService를 생성자로 자동 주입
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    // POST /missions/{missionId}/reviews?memberId=1
    @PostMapping("/missions/{missionId}/reviews")
    public ApiResponse<ReviewResDTO.CreateReviewRes> createReview(
            @RequestParam Long memberId,            // 임시 - 나중에 JWT 토큰으로 대체 예정
            @PathVariable Long missionId,           // URL 경로에서 미션 ID 받기
            @RequestBody ReviewReqDTO.CreateReview dto // 요청 바디에서 리뷰 내용 받기
    ) {
        return ApiResponse.onSuccess(ReviewSuccessCode.CREATE_REVIEW_OK, reviewService.createReview(memberId, missionId, dto));
    }

    // 내가 작성한 리뷰 목록 조회
    // GET /mypage/reviews?memberId=1
    @GetMapping("/mypage/reviews")
    public ApiResponse<ReviewResDTO.ReviewListRes> getMyReviews(
            @RequestParam Long memberId // 임시 - 나중에 JWT 토큰으로 대체 예정
    ) {
        return ApiResponse.onSuccess(ReviewSuccessCode.GET_REVIEWS_OK, reviewService.getMyReviews(memberId));
    }
}
