package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Reply;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static ReviewResDTO.ReviewReplyDTO toReplyDTO(Reply reply) {
        return ReviewResDTO.ReviewReplyDTO.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .memberNickname(reply.getMember().getNickname())
                .createdAt(reply.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.ReviewDTO toReviewDTO(Review review) {
        List<String> imageUrls = review.getPhotos().stream()
                .map(ReviewPhoto::getImageUrl)
                .collect(Collectors.toList());
        List<ReviewResDTO.ReviewReplyDTO> replies = review.getReplies().stream()
                .map(ReviewConverter::toReplyDTO)
                .collect(Collectors.toList());
        return ReviewResDTO.ReviewDTO.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrls(imageUrls)
                .memberNickname(review.getMember().getNickname())
                .createdAt(review.getCreatedAt())
                .replies(replies)
                .build();
    }

    public static ReviewResDTO.ReviewListDTO toReviewListDTO(Page<Review> page, Double avgRating) {
        List<ReviewResDTO.ReviewDTO> reviews = page.getContent().stream()
                .map(ReviewConverter::toReviewDTO)
                .collect(Collectors.toList());
        return ReviewResDTO.ReviewListDTO.builder()
                .reviews(reviews)
                .avgRating(avgRating != null ? avgRating : 0.0)
                .totalCount((int) page.getTotalElements())
                .currentPage(page.getNumber())
                .build();
    }

    public static ReviewResDTO.ReviewCreateResultDTO toReviewCreateResultDTO(Review review) {
        return ReviewResDTO.ReviewCreateResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.ReviewReplyCreateResultDTO toReplyCreateResultDTO(Reply reply) {
        return ReviewResDTO.ReviewReplyCreateResultDTO.builder()
                .replyId(reply.getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
