package com.example.umc10th.domain.store.dto;

import java.time.LocalDateTime;

public class StoreResDTO {

    // 리뷰 작성 응답
    public record CreateReviewResDTO(
            LocalDateTime createdAt
    ){}
}
