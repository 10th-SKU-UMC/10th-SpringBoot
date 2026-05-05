package com.example.umc10th.review.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.review.dto.ReviewReqDTO;
import com.example.umc10th.review.dto.ReviewResDTO;
import com.example.umc10th.review.exception.ReviewException;
import com.example.umc10th.review.exception.code.ReviewErrorCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

	@PostMapping("/stores/{storeId}/reviews")
	public ApiResponse<ReviewResDTO.CreateReviewResponse> createReview(
			@PathVariable Long storeId,
			@RequestBody ReviewReqDTO.CreateReviewRequest request
	) {
		if (request.rating() == null || request.rating() < 1 || request.rating() > 5) {
			throw new ReviewException(ReviewErrorCode.REVIEW_BAD_REQUEST);
		}

		ReviewResDTO.CreateReviewResponse result = new ReviewResDTO.CreateReviewResponse(
				1L,
				storeId,
				request.missionId(),
				request.rating(),
				request.content()
		);

		return ApiResponse.onSuccess("REVIEW201", "리뷰 작성 성공", result);
	}
}

