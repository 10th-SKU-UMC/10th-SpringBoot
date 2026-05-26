package com.umc10th.umc10th.domain.user.repository;

import com.umc10th.umc10th.domain.user.entity.mapping.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
}
