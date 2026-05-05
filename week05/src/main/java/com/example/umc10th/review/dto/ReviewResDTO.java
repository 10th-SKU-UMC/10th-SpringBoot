package com.example.umc10th.review.dto;

public class ReviewResDTO {

	public record CreateReviewResponse(
			Long reviewId,
			Long storeId,
			Long missionId,
			Integer rating,
			String content
	) {
	}
}

