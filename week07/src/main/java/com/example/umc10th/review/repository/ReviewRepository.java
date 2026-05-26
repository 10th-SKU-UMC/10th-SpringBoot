package com.example.umc10th.review.repository;

import com.example.umc10th.review.entity.Review;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(
            value = "select r from Review r where r.store.id = :storeId order by r.createdAt desc",
            countQuery = "select count(r) from Review r where r.store.id = :storeId"
    )
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    // 커서 기반 - ID 순
    @Query("select r from Review r where r.member.id = :memberId order by r.id desc")
    Slice<Review> findByMemberIdOrderByIdDesc(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select r from Review r where r.member.id = :memberId and r.id < :lastId order by r.id desc")
    Slice<Review> findByMemberIdAndIdLessThanOrderByIdDesc(
            @Param("memberId") Long memberId,
            @Param("lastId") Long lastId,
            Pageable pageable
    );

    // 커서 기반 - 별점 순 (높은 별점부터, 동점이면 ID 내림차순)
    @Query("select r from Review r where r.member.id = :memberId order by r.star desc, r.id desc")
    Slice<Review> findByMemberIdOrderByStarDescIdDesc(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select r from Review r where r.member.id = :memberId and (r.star < :lastStar or (r.star = :lastStar and r.id < :lastId)) order by r.star desc, r.id desc")
    Slice<Review> findByMemberIdWithStarCursorOrderByStarDescIdDesc(
            @Param("memberId") Long memberId,
            @Param("lastStar") BigDecimal lastStar,
            @Param("lastId") Long lastId,
            Pageable pageable
    );
}
