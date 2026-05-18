package com.example.week07.domain.member.entity;

import com.example.week07.domain.member.entity.mapping.MemberFood;
import com.example.week07.domain.member.entity.mapping.MemberTerm;
import com.example.week07.global.entity.BaseEntity;
import com.example.week07.global.enums.Address;
import com.example.week07.domain.member.enums.Gender;
import com.example.week07.domain.member.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity // DB 테이블과 1:1 매핑되는 클래스임을 선언
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA가 객체 생성할 때 쓰는 기본 생성자 (외부에서 직접 호출 막음)
@AllArgsConstructor
@Table(name = "member") // 매핑할 DB 테이블 이름
public class Member extends BaseEntity { // BaseEntity: createdAt, updatedAt, deletedAt 자동 관리

    @Id // PK 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 증가 (1, 2, 3...)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 5) // NOT NULL, 최대 5글자
    private String name;

    @Builder.Default // 빌더 사용 시 기본값 NONE으로 설정
    @Enumerated(EnumType.STRING) // Enum을 숫자가 아닌 문자열로 DB에 저장
    @Column(name = "gender", nullable = false)
    private Gender gender = Gender.NONE;

    @Column(name = "birth", nullable = false)
    private LocalDate birth; // 생년월일

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "address", nullable = false)
    private Address address = Address.ETC; // 기본값 ETC

    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress; // 상세 주소

    @Column(name = "social_uid", nullable = false, length = 255)
    private String socialUid; // 소셜 로그인 고유 ID (OAuth UID)

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType; // 소셜 로그인 타입 (KAKAO, NAVER, APPLE, GOOGLE)

    @Builder.Default
    @Column(name = "point", nullable = false)
    private Integer point = 0; // 보유 포인트, 기본값 0

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone_number", length = 11) // NULL 허용 (선택 입력)
    private String phoneNumber;

    @Column(name = "profile_url", nullable = false, columnDefinition = "TEXT")
    private String profileUrl; // 프로필 이미지 URL

    // 이 회원이 선택한 선호 음식 목록 (member_food 테이블과 연결)
    // mappedBy = "member" → MemberFood 클래스의 member 필드가 관계의 주인
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberFood> memberFoods = new ArrayList<>();

    // 이 회원이 동의한 약관 목록 (member_term 테이블과 연결)
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberTerm> memberTerms = new ArrayList<>();
}
