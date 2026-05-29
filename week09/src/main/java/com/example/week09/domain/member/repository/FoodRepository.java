package com.example.week09.domain.member.repository;

import com.example.week09.domain.member.entity.Food;
import com.example.week09.domain.member.enums.FoodName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    // 음식 이름으로 Food 조회
    Optional<Food> findByName(FoodName name);
}
