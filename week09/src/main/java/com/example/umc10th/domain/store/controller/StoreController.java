package com.example.umc10th.domain.store.controller;


import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.dto.StoreResDTO;
import com.example.umc10th.domain.store.entity.Location;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.code.StoreSuccessCode;
import com.example.umc10th.domain.store.service.StoreService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class StoreController {

    private final StoreService storeService;

    // 리뷰 작성하기
    @PostMapping("/v1/stores/{store_id}/reviews")
    public ApiResponse<StoreResDTO.CreateReviewResDTO> createReview(
            @PathVariable(name = "store_id") Long store_id, // 경로 지정, 주소의 값을 꺼내서 사용
            @RequestBody @Valid StoreReqDTO.CreateReviewReqDTO request
    ){
        return ApiResponse.onSuccess(StoreSuccessCode.OK_CREATE_REVIEW, null);
    }

    // 가게 추가하기(임시)
    @PostMapping("/v1/stores")
    public ApiResponse<Long> createStore(
            @RequestBody StoreReqDTO.CreateStoreReqDTO request
    ){
        Store store = storeService.createStore(request);
        BaseSuccessCode code = StoreSuccessCode.OK_CREATE_STORE;
        return ApiResponse.onSuccess(code, store.getId());
    }

    // 지역 추가하기(임시)
    @PostMapping("/v1/locations")
    public ApiResponse<Long> createLocation(
            @RequestParam(name = "name")
            String name
    ){
        Location location = storeService.createLocation(name);
        BaseSuccessCode code = StoreSuccessCode.OK_CREATE_LOCATION;
        return ApiResponse.onSuccess(code, location.getId());
    }
}
