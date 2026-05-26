package com.umc10th.umc10th.domain.category.entity;

import com.umc10th.umc10th.domain.store.entity.Store;
import com.umc10th.umc10th.domain.user.entity.mapping.UserCategory;
import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자 접근 제어
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Builder.Default // 빌더 사용 시 빈 리스트로 초기화 보장
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Store> storeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<UserCategory> userCategoryList = new ArrayList<>();

}