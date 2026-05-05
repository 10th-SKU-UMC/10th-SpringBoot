package com.umc10th.umc10th.domain.user.entity;

import com.umc10th.umc10th.domain.user.enums.Gender;
import com.umc10th.umc10th.domain.user.enums.Provider;
import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder // 빌더 패턴 사용
@AllArgsConstructor // 빌더를 위해 모든 필드 생성자 필요
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자 (보안상 protected)
public class User extends   BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_point_id")
    private UserPoint userPoint;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birth;

    //이메일 추가
    @Column(nullable = false)
    private String email;

    //프로필 사진 주소
    private String profileUrl;

    //전화번호
    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Provider social_provider;

    private String social_uid;

    private LocalDateTime deletedAt;
}
