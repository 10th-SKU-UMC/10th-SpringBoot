package com.example.umc10th.review.repository;

import com.example.umc10th.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(
            value = "select r from Review r where r.store.id = :storeId order by r.createdAt desc",
            countQuery = "select count(r) from Review r where r.store.id = :storeId"
    )
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
