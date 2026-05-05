package com.umc10th.umc10th.domain.store.controller;

import com.umc10th.umc10th.domain.store.dto.StoreReqDto;
import com.umc10th.umc10th.domain.store.dto.StoreResDto;
import com.umc10th.umc10th.domain.store.service.StoreService;
import com.umc10th.umc10th.domain.user.exception.code.BaseSuccessCode;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StoreController {


    private final StoreService storeService;

    //가게 상세보기
    @GetMapping("/api/stores/{storeId}")
    public ApiResponse<StoreResDto.StoreDetailDto> getStoreDetail(@PathVariable Long storeId) {

        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    //가게 별 수행 가능 미션 보기
    @GetMapping("/api/stores/{storeId}/missions")
    public ApiResponse<StoreResDto.MissionListDto> getStoreMissions(
            @PathVariable Long storeId
    ) {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    //가게 모든 리류 보기
    @GetMapping("/api/stores{storeId}/reviews")
    public ApiResponse<StoreResDto.ReviewListDto> getStoreAllReview() {

        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    //리뷰 쓰기(완료한 미션에 대해)
    @PostMapping("/api/users/{userId}stores/{storeId}/missions/{missionId}/reviews")
    public ApiResponse<StoreResDto.ReviewDetailDto> createReview(
            @PathVariable Long userId,
            @PathVariable Long storeId,
            @PathVariable Long missionId,
            @RequestBody StoreReqDto.ReviewCreateDto request
            ) {

        StoreReqDto.ReviewCreateCommandDto command = StoreReqDto.ReviewCreateCommandDto.builder()
                .userId(userId)
                .storeId(storeId)
                .missionId(missionId)
                .detail(request)
                .build();

        StoreResDto.ReviewDetailDto result = storeService.createReview(command);

        return ApiResponse.onSuccess(BaseSuccessCode.OK, result);
    }
}
