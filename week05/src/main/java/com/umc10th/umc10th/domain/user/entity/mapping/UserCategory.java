package com.umc10th.umc10th.domain.user.entity.mapping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserCategory {

    @Id @Column(name = "user_category")
    private Long id;
}
