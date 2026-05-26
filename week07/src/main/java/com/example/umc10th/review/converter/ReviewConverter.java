package com.example.umc10th.review.converter;

import com.example.umc10th.review.dto.ReviewResDTO;
import com.example.umc10th.review.entity.Review;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public final class ReviewConverter {

    private ReviewConverter() {}

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
                review.getStar(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    public static ReviewResDTO.ReviewPageResponse toReviewPageResponse(Page<Review> reviewPage) {
        List<ReviewResDTO.ReviewSummary> items = reviewPage.getContent().stream()
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

    public static ReviewResDTO.MyReviewCursorResponse toMyReviewCursorResponse(
            Slice<Review> slice,
            String sortBy
    ) {
        List<Review> content = slice.getContent();
        boolean hasNext = slice.hasNext();

        Long nextIdCursor = null;
        BigDecimal nextStarCursor = null;

        if (hasNext && !content.isEmpty()) {
            Review last = content.get(content.size() - 1);
            nextIdCursor = last.getId();
            if ("RATING".equalsIgnoreCase(sortBy)) {
                nextStarCursor = last.getStar();
            }
        }

        List<ReviewResDTO.ReviewSummary> summaries = content.stream()
                .map(ReviewConverter::toReviewSummary)
                .toList();

        return new ReviewResDTO.MyReviewCursorResponse(summaries, nextIdCursor, nextStarCursor, hasNext);
    }
}
