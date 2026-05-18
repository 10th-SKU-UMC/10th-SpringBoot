package com.example.week07.domain.member.entity;

import com.example.week07.domain.member.enums.FoodName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "food")

public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private FoodName name;
}
