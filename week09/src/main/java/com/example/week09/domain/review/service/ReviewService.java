package com.example.week09.domain.review.service;

import com.example.week09.domain.member.entity.Member;
import com.example.week09.domain.member.exception.MemberException;
import com.example.week09.domain.member.exception.code.MemberErrorCode;
import com.example.week09.domain.member.repository.MemberRepository;
import com.example.week09.domain.mission.entity.Mission;
import com.example.week09.domain.mission.exception.MissionException;
import com.example.week09.domain.mission.exception.code.MissionErrorCode;
import com.example.week09.domain.mission.repository.MissionRepository;
import com.example.week09.domain.review.converter.ReviewConverter;
import com.example.week09.domain.review.dto.ReviewReqDTO;
import com.example.week09.domain.review.dto.ReviewResDTO;
import com.example.week09.domain.review.entity.Review;
import com.example.week09.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    // 내가 작성한 리뷰 목록 조회 (커서 기반 페이지네이션)
    // query: "id"(ID순) 또는 "star"(별점순)
    // cursor: "id:10" 또는 "star:4.5:10" 형태, 없으면 null
    public ReviewResDTO.MyReviewListRes getMyReviews(Long memberId, String query, String cursor, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Slice<Review> slice;
        String nextCursor;

        if ("star".equals(query)) {
            // 별점 순 조회
            if (cursor == null) {
                // 커서 없음 → 처음부터 조회
                slice = reviewRepository.findByMemberIdOrderByStarDesc(memberId, pageRequest);
            } else {
                // 커서 파싱: "star:4.5:10" → star=4.5, id=10
                String[] parts = cursor.split(":");
                BigDecimal cursorStar = new BigDecimal(parts[1]);
                Long cursorId = Long.parseLong(parts[2]);
                slice = reviewRepository.findByMemberIdOrderByStarDescWithCursor(memberId, cursorStar, cursorId, pageRequest);
            }
            // 다음 커서 계산: 마지막 리뷰의 star:id
            nextCursor = slice.hasNext()
                    ? "star:" + slice.getContent().get(slice.getContent().size() - 1).getStar()
                    + ":" + slice.getContent().get(slice.getContent().size() - 1).getId()
                    : null;
        } else {
            // ID 순 조회 (기본)
            if (cursor == null) {
                // 커서 없음 → 처음부터 조회
                slice = reviewRepository.findByMember_IdOrderByIdDesc(memberId, pageRequest);
            } else {
                // 커서 파싱: "id:10" → id=10
                Long cursorId = Long.parseLong(cursor.split(":")[1]);
                slice = reviewRepository.findByMember_IdAndIdLessThanOrderByIdDesc(memberId, cursorId, pageRequest);
            }
            // 다음 커서 계산: 마지막 리뷰의 id
            nextCursor = slice.hasNext()
                    ? "id:" + slice.getContent().get(slice.getContent().size() - 1).getId()
                    : null;
        }

        return ReviewConverter.toMyReviewListRes(slice, nextCursor);
    }
}
