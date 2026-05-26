package com.example.umc10th.domain.store.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class StoreResDTO {

    // 리뷰 작성 응답
    @Builder
    public record CreateReviewResDTO(
            LocalDateTime createdAt
    ){}

    // 나의 리뷰 조회 응답
    @Builder
    public record GetMemberReviewResDTO(
            Long reviewId,
            String storeName,
            Float score,
            String content,
            LocalDateTime createdAt
    ){}

    // 페이지네이션 툴
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}
}
