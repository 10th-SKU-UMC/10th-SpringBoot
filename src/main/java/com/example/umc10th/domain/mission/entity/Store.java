package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.mission.enums.StoreCategory;
import com.example.umc10th.domain.review.entity.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// domain/mission/entity/Store.java
@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String storeName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ETC'")
  private StoreCategory storeCategory;

  @Column(nullable = false)
  private String address;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "region_id", nullable = false)
  private Location location;        // Location = region 테이블

  // 연관관계
  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
  private List<Mission> missions = new ArrayList<>();

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
  private List<Review> reviews = new ArrayList<>();
}