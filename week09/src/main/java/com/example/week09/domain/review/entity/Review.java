package com.example.week09.domain.review.entity;

import com.example.week09.domain.member.entity.Member;
import com.example.week09.domain.mission.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 리뷰 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성일자

    @Column(name = "star", nullable = false)
    private BigDecimal star; // 별점 (소수점 허용, 예: 4.5)

    // 리뷰가 작성된 가게 (store 테이블과 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 리뷰를 작성한 회원 (member 테이블과 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    // 리뷰에 달린 답글 (reply 테이블과 연결) - 리뷰 작성 시점엔 없을 수 있으므로 nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    // 리뷰에 첨부된 사진 목록 (review_photo 테이블과 연결)
    @Builder.Default
    @OneToMany(mappedBy = "review")
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();
}
