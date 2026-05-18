package com.example.week06.domain.review.repository;

import com.example.week06.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 가게의 리뷰 목록을 최신순으로 페이징 조회
    // Page<Review>: 페이징된 결과 (전체 수, 현재 페이지 등 포함)
    @Query("SELECT r FROM Review r WHERE r.store.id = :storeId ORDER BY r.createdAt DESC")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
