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
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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

    @Column(nullable = false, unique = true) // 이메일은 중복되면 안됨
    private String email;

    private String profileUrl;

    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Provider socialProvider;

    private String socialUid;

    private LocalDateTime deletedAt;
}