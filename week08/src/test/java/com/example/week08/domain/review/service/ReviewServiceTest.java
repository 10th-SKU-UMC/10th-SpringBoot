package com.example.week08.domain.review.service;

import com.example.week08.domain.member.repository.MemberRepository;
import com.example.week08.domain.mission.entity.Store;
import com.example.week08.domain.mission.repository.MissionRepository;
import com.example.week08.domain.review.dto.ReviewResDTO;
import com.example.week08.domain.review.entity.Review;
import com.example.week08.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock ReviewRepository reviewRepository;
    @Mock MemberRepository memberRepository;
    @Mock MissionRepository missionRepository;

    @InjectMocks ReviewService reviewService;

    // 테스트용 Review 엔티티 생성 헬퍼
    private Review createReview(Long id, BigDecimal star) {
        Store store = mock(Store.class);
        when(store.getName()).thenReturn("테스트 가게");

        Review review = mock(Review.class);
        when(review.getId()).thenReturn(id);
        when(review.getStore()).thenReturn(store);
        when(review.getStar()).thenReturn(star);
        when(review.getContent()).thenReturn("맛있어요!");
        when(review.getCreatedAt()).thenReturn(LocalDateTime.of(2024, 1, 1, 12, 0));
        when(review.getReply()).thenReturn(null); // 사장님 답글 없음
        return review;
    }

    @Test
    @DisplayName("내 리뷰 조회 - ID순 커서 없음 (처음 조회)")
    void getMyReviews_ID순_커서없음_성공() {
        // given
        Long memberId = 1L;
        Review review = createReview(10L, new BigDecimal("4.5"));
        Slice<Review> slice = new SliceImpl<>(List.of(review), PageRequest.of(0, 10), true);
        given(reviewRepository.findByMember_IdOrderByIdDesc(eq(memberId), any()))
                .willReturn(slice);

        // when
        ReviewResDTO.MyReviewListRes result = reviewService.getMyReviews(memberId, "id", null, 10);

        // then
        assertThat(result.reviews()).hasSize(1);
        assertThat(result.hasNext()).isTrue();
        assertThat(result.nextCursor()).isEqualTo("id:10"); // 마지막 리뷰 ID로 커서 생성
        assertThat(result.listSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("내 리뷰 조회 - ID순 커서 있음 (다음 페이지)")
    void getMyReviews_ID순_커서있음_성공() {
        // given
        Long memberId = 1L;
        Review review = createReview(5L, new BigDecimal("4.0"));
        Slice<Review> slice = new SliceImpl<>(List.of(review), PageRequest.of(0, 10), false);
        given(reviewRepository.findByMember_IdAndIdLessThanOrderByIdDesc(eq(memberId), eq(10L), any()))
                .willReturn(slice);

        // when
        ReviewResDTO.MyReviewListRes result = reviewService.getMyReviews(memberId, "id", "id:10", 10);

        // then
        assertThat(result.reviews()).hasSize(1);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.nextCursor()).isNull(); // 마지막 페이지이므로 커서 없음
    }

    @Test
    @DisplayName("내 리뷰 조회 - 별점순 커서 없음 (처음 조회)")
    void getMyReviews_별점순_커서없음_성공() {
        // given
        Long memberId = 1L;
        Review review = createReview(10L, new BigDecimal("4.5"));
        Slice<Review> slice = new SliceImpl<>(List.of(review), PageRequest.of(0, 10), true);
        given(reviewRepository.findByMemberIdOrderByStarDesc(eq(memberId), any()))
                .willReturn(slice);

        // when
        ReviewResDTO.MyReviewListRes result = reviewService.getMyReviews(memberId, "star", null, 10);

        // then
        assertThat(result.reviews()).hasSize(1);
        assertThat(result.hasNext()).isTrue();
        assertThat(result.nextCursor()).isEqualTo("star:4.5:10"); // star:별점:id 형태
    }

    @Test
    @DisplayName("내 리뷰 조회 - 별점순 커서 있음 (다음 페이지)")
    void getMyReviews_별점순_커서있음_성공() {
        // given
        Long memberId = 1L;
        Review review = createReview(5L, new BigDecimal("3.0"));
        Slice<Review> slice = new SliceImpl<>(List.of(review), PageRequest.of(0, 10), false);
        given(reviewRepository.findByMemberIdOrderByStarDescWithCursor(
                eq(memberId), eq(new BigDecimal("4.5")), eq(10L), any()))
                .willReturn(slice);

        // when
        ReviewResDTO.MyReviewListRes result = reviewService.getMyReviews(memberId, "star", "star:4.5:10", 10);

        // then
        assertThat(result.reviews()).hasSize(1);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.nextCursor()).isNull();
    }
}
