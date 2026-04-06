package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private String nickname;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String address;

  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'NONE'")
  private Gender gender;

  @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
  private Integer point;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;    // soft delete

  // 연관관계
  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<Review> reviews = new ArrayList<>();

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<MemberMission> memberMissions = new ArrayList<>();

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<MemberFood> memberFoods = new ArrayList<>();  // preferences

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<MemberTerm> memberTerms = new ArrayList<>();
}
