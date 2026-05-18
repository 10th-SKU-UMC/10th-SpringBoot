package com.example.week07.domain.review.controller;

import com.example.week07.domain.review.dto.ReviewReqDTO;
import com.example.week07.domain.review.dto.ReviewResDTO;
import com.example.week07.domain.review.exception.code.ReviewSuccessCode;
import com.example.week07.domain.review.service.ReviewService;
import com.example.week07.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
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
            @Valid @RequestBody ReviewReqDTO.CreateReview dto // 요청 바디에서 리뷰 내용 받기 + 검증
    ) {
        return ApiResponse.onSuccess(ReviewSuccessCode.CREATE_REVIEW_OK, reviewService.createReview(memberId, missionId, dto));
    }

    // 내가 작성한 리뷰 목록 조회 (커서 기반 페이지네이션)
    // GET /mypage/reviews?memberId=1&query=id&cursor=id:10&size=10
    @GetMapping("/mypage/reviews")
    public ApiResponse<ReviewResDTO.MyReviewListRes> getMyReviews(
            @RequestParam Long memberId,                          // 임시 - 나중에 JWT 토큰으로 대체 예정
            @RequestParam(defaultValue = "id") String query,     // 정렬 기준: id(ID순) 또는 star(별점순)
            @RequestParam(required = false) String cursor,        // 커서값 (없으면 처음부터 조회)
            @RequestParam(defaultValue = "10") int size           // 페이지 크기
    ) {
        return ApiResponse.onSuccess(ReviewSuccessCode.GET_REVIEWS_OK, reviewService.getMyReviews(memberId, query, cursor, size));
    }

    // 가게 리뷰 목록 조회 (페이징)
    // GET /stores/{storeId}/reviews?page=0&size=10
    @GetMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.StoreReviewListRes> getStoreReviews(
            @PathVariable Long storeId,              // URL 경로에서 가게 ID 받기
            @RequestParam(defaultValue = "0") int page,   // 페이지 번호 (기본값 0)
            @RequestParam(defaultValue = "10") int size   // 페이지 크기 (기본값 10)
    ) {
        return ApiResponse.onSuccess(ReviewSuccessCode.GET_STORE_REVIEWS_OK, reviewService.getStoreReviews(storeId, page, size));
    }
}
