package com.example.umc10th.review.converter;

import com.example.umc10th.review.dto.ReviewResDTO;
import com.example.umc10th.review.entity.Review;
import java.util.List;
import org.springframework.data.domain.Page;

public final class ReviewConverter {

    private ReviewConverter() {
    }

    public static ReviewResDTO.CreateReviewResponse toCreateReviewResponse(
            Review review,
            Long storeId,
            Long missionId
    ) {
        return new ReviewResDTO.CreateReviewResponse(
                review.getId(),
                storeId,
                missionId,
                review.getStar().intValue(),
                review.getContent()
        );
    }

    public static ReviewResDTO.ReviewSummary toReviewSummary(Review review) {
        return new ReviewResDTO.ReviewSummary(
                review.getId(),
                review.getStar().intValue(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    public static ReviewResDTO.ReviewPageResponse toReviewPageResponse(Page<Review> reviewPage) {
        List<ReviewResDTO.ReviewSummary> items = reviewPage
                .getContent()
                .stream()
                .map(ReviewConverter::toReviewSummary)
                .toList();

        return new ReviewResDTO.ReviewPageResponse(
                items,
                reviewPage.getNumber(),
                reviewPage.getSize(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.hasNext()
        );
    }
}
