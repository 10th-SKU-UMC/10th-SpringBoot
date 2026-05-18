package com.example.week06.domain.review.service;

import com.example.week06.domain.member.entity.Member;
import com.example.week06.domain.member.exception.MemberException;
import com.example.week06.domain.member.exception.code.MemberErrorCode;
import com.example.week06.domain.member.repository.MemberRepository;
import com.example.week06.domain.mission.entity.Mission;
import com.example.week06.domain.mission.exception.MissionException;
import com.example.week06.domain.mission.exception.code.MissionErrorCode;
import com.example.week06.domain.mission.repository.MissionRepository;
import com.example.week06.domain.review.converter.ReviewConverter;
import com.example.week06.domain.review.dto.ReviewReqDTO;
import com.example.week06.domain.review.dto.ReviewResDTO;
import com.example.week06.domain.review.entity.Review;
import com.example.week06.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    // 리뷰 작성
    @Transactional // DB에 데이터를 저장하므로 @Transactional 필수
    public ReviewResDTO.CreateReviewRes createReview(Long memberId, Long missionId, ReviewReqDTO.CreateReview dto) {
        // 회원 조회 - 없으면 예외 발생
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 미션 조회 - 없으면 예외 발생
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        // 미션에서 가게 정보 가져오기
        Review review = ReviewConverter.toReview(dto, member, mission.getStore());

        // DB에 저장
        reviewRepository.save(review);

        return ReviewConverter.toCreateReviewRes(review);
    }

    // 가게 리뷰 목록 조회 (페이징)
    public ReviewResDTO.StoreReviewListRes getStoreReviews(Long storeId, int page, int size) {
        Page<Review> reviewPage = reviewRepository.findByStoreId(
                storeId,
                PageRequest.of(page, size)
        );
        return ReviewConverter.toStoreReviewListRes(reviewPage);
    }

    // 내가 작성한 리뷰 목록 조회 - 다음 주차에 구현 예정
    public ReviewResDTO.ReviewListRes getMyReviews(Long memberId) {
        return null;
    }
}
