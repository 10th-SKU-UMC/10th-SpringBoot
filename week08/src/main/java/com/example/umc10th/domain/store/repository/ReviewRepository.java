package com.example.umc10th.domain.store.repository;

import com.example.umc10th.domain.store.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // id순, 최신순 커서 기준 조회
    Slice<Review> findAllByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long id, Pageable pageable);

    // 별점 순 커서 기준 조회
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId AND " +
            "(r.star < :score OR (r.star = :score AND r.id < :id)) " +
            "ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findAllByMemberIdAndScoreCursorOrder(Long memberId, Float score, Long id, Pageable pageable);
}
