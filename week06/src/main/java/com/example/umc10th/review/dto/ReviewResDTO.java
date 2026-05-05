package com.example.umc10th.review.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

	public record CreateReviewResponse(
			Long reviewId,
			Long storeId,
			Long missionId,
			Integer rating,
			String content
	) {
	}

	public record ReviewSummary(
			Long reviewId,
			Integer rating,
			String content,
			LocalDateTime createdAt
	) {
	}

	public record ReviewPageResponse(
			List<ReviewSummary> reviews,
			int page,
			int size,
			long totalElements,
			int totalPages,
			boolean hasNext
	) {
	}
}
