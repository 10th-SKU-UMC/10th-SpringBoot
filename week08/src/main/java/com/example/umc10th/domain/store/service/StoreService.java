package com.example.umc10th.domain.store.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.store.converter.StoreConverter;
import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.dto.StoreResDTO;
import com.example.umc10th.domain.store.entity.Location;
import com.example.umc10th.domain.store.entity.Review;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.exception.code.StoreErrorCode;
import com.example.umc10th.domain.store.repository.LocationRepository;
import com.example.umc10th.domain.store.repository.ReviewRepository;
import com.example.umc10th.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;
    private final MemberRepository memberRepository;

    // 리뷰 작성 로직
    @Transactional
    public Review createReview(Long storeId, StoreReqDTO.CreateReviewReqDTO request){
        Member member = memberRepository.findById(request.userId())
                .orElseThrow(()-> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        Review newReview = StoreConverter.toCreateReview(request, member, store);
        return reviewRepository.save(newReview);
    }
    
    // 가게 생성 로직(임시)
    @Transactional
    public Store createStore(StoreReqDTO.CreateStoreReqDTO dto){
        // 지역 존재 확인
        Location location = locationRepository.findById(dto.locationId())
                .orElseThrow(()-> new RuntimeException("해당 지역이 존재하지 않습니다"));
        Store newStore = StoreConverter.toCreateStore(location, dto);
        return storeRepository.save(newStore);
    }

    // 지역 생성 로직(임시)
    @Transactional
    public Location createLocation(String name){
        Location newLocation = StoreConverter.toCreateLocation(name);
        return locationRepository.save(newLocation);
    }

    // 나의 리뷰 목록 조회 로직
    @Transactional
    public StoreResDTO.Pagination<StoreResDTO.GetMemberReviewResDTO> getMemberReviewList(
            StoreReqDTO.GetMemberReviewReqDTO request
    ){
        PageRequest pageRequest =  PageRequest.of(0, request.pageSize());
        Slice<Review> reviewSlice;
        String nextCursor = null;

        // 정렬 기준에 따른 분기
        if (request.query().equals("id")){
            Long idCursor;
            if (request.cursor().equals("-1")){
                idCursor = Long.MAX_VALUE;
            }else{
                idCursor = Long.parseLong(request.cursor());
            }
            reviewSlice = reviewRepository.findAllByMemberIdAndIdLessThanOrderByIdDesc(request.memberId(), idCursor, pageRequest);
        }else if (request.query().equals("score")){
            Float scoreCursor = 5.1f;
            Long idCursor = Long.MAX_VALUE;

            if (!request.cursor().equals("-1")){
                String[] parts = request.cursor().split(":");
                scoreCursor = Float.parseFloat(parts[0]);
                idCursor = Long.parseLong(parts[1]);
            }
            reviewSlice = reviewRepository.findAllByMemberIdAndScoreCursorOrder(request.memberId(), scoreCursor, idCursor, pageRequest);
        }else{
            throw new StoreException(StoreErrorCode.QUERY_NOT_VALID);
        }

        // 2. 다음 커서 계산
        if (reviewSlice.hasContent() && reviewSlice.hasNext()) {
            Review lastReview = reviewSlice.getContent().getLast();
            nextCursor = request.query().equalsIgnoreCase("id")
                    ? lastReview.getId().toString()
                    : lastReview.getStar() + ":" + lastReview.getId();
        }
        return StoreConverter.toPagination(
                reviewSlice.map(StoreConverter::toGetMemberReview).toList(),
                reviewSlice.hasNext(),
                nextCursor,
                reviewSlice.getSize()
        );
    }
}
