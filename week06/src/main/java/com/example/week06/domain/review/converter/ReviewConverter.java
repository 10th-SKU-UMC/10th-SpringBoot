package com.example.week06.domain.review.converter;

import com.example.week06.domain.member.entity.Member;
import com.example.week06.domain.mission.entity.Store;
import com.example.week06.domain.review.dto.ReviewReqDTO;
import com.example.week06.domain.review.dto.ReviewResDTO;
import com.example.week06.domain.review.entity.Review;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewConverter {

    // 요청 DTO + 회원 + 가게 정보로 Review 엔티티 생성
    public static Review toReview(ReviewReqDTO.CreateReview dto, Member member, Store store) {
        return Review.builder()
                .content(dto.content())                         // 리뷰 내용
                .star(BigDecimal.valueOf(dto.rating()))         // Integer → BigDecimal 변환
                .createdAt(LocalDateTime.now())                 // 현재 시간
                .member(member)                                 // 작성자
                .store(store)                                   // 가게
                // .photoUrl(dto.photoUrl())                    // 사진 URL (스킵)
                .build();
    }

    // 생성된 Review 엔티티를 응답 DTO로 변환
    public static ReviewResDTO.CreateReviewRes toCreateReviewRes(Review review) {
        return ReviewResDTO.CreateReviewRes.builder()
                .reviewId(review.getId())
                .build();
    }

    // Review 엔티티 하나를 응답 DTO로 변환
    public static ReviewResDTO.StoreReviewInfo toStoreReviewInfo(Review review) {
        return ReviewResDTO.StoreReviewInfo.builder()
                .memberName(review.getMember().getName())  // 작성자 이름
                .createdAt(review.getCreatedAt().toString()) // 작성일 문자열 변환
                .star(review.getStar())                    // 별점
                .content(review.getContent())              // 리뷰 내용
                .build();
    }

    // Review 페이징 결과를 응답 DTO로 변환
    public static ReviewResDTO.StoreReviewListRes toStoreReviewListRes(Page<Review> reviewPage) {
        List<ReviewResDTO.StoreReviewInfo> reviews = reviewPage.getContent()
                .stream()
                .map(ReviewConverter::toStoreReviewInfo) // 각 리뷰를 DTO로 변환
                .toList();

        return ReviewResDTO.StoreReviewListRes.builder()
                .reviews(reviews)
                .totalCount((int) reviewPage.getTotalElements()) // 전체 리뷰 수
                .hasNext(reviewPage.hasNext())                   // 다음 페이지 존재 여부
                .build();
    }
}
