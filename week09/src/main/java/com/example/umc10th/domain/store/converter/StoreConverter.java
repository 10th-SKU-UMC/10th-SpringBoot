package com.example.umc10th.domain.store.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.dto.StoreResDTO;
import com.example.umc10th.domain.store.entity.Location;
import com.example.umc10th.domain.store.entity.Review;
import com.example.umc10th.domain.store.entity.Store;

import java.time.LocalDateTime;
import java.util.List;

public class StoreConverter {

    // 리뷰 작성
    public static Review toCreateReview(
            StoreReqDTO.CreateReviewReqDTO request,
            Member member,
            Store store
    ){
        return Review.builder()
                .star(request.star())
                .content(request.content())
                .member(member)
                .store(store)
                .build();
    }

    // 가게 생성(임시)
    public static Store toCreateStore(Location location, StoreReqDTO.CreateStoreReqDTO dto){
        return Store.builder()
                .name(dto.name())
                .address(dto.address())
                .location(location)
                .build();
    }

    // 지역 생성(임시)
    public static Location toCreateLocation(String name){
        return Location.builder()
                .name(name)
                .build();
    }

    // 페이지네이션 툴 생성
    public static <T> StoreResDTO.Pagination<T> toPagination(
            List<T> data,
            //Integer pageNumber,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){
        return StoreResDTO.Pagination.<T>builder()
                .data(data)
                //.pageNumber(pageNumber)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }

    // 나의 리뷰 조회
    public static StoreResDTO.GetMemberReviewResDTO toGetMemberReview(
            Review review
    ){
        return StoreResDTO.GetMemberReviewResDTO.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .score(review.getStar())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
