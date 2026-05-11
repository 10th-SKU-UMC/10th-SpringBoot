package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN FETCH r.member WHERE r.store.id = :storeId ORDER BY r.createdAt DESC")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId ORDER BY r.createdAt DESC")
    Page<Review> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.store.id = :storeId")
    Double findAvgRatingByStoreId(@Param("storeId") Long storeId);
}
