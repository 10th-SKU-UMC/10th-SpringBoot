package com.example.umc10th.domain.store.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StoreReqDTO {

    // 리류 작성 요청
    public record CreateReviewReqDTO(
            @NotNull(message = "유저 아이디는 필수입니다.")
            Long userId,
            @NotBlank(message = "리뷰 내용은 공백일 수 없습니다.")
            String content,
            @DecimalMin(value = "0.0", message = "별점은 0점 이상이어야 합니다.")
            @DecimalMax(value = "5.0", message = "별점은 5점 이하여야 합니다.")
            Float star
    ){}

    // 가게 생성 요청
    public record CreateStoreReqDTO(
            String name,
            String address,
            Long locationId
    ){}

    // 나의 리뷰 조회 요첳
    public record GetMemberReviewReqDTO(
            @NotNull
            Long memberId,
            @NotNull
            Integer pageSize,
            String cursor,
            @NotBlank
            String query    // id또는 score
    ){}
}
