package com.umc10th.umc10th.domain.user.repository;

import com.umc10th.umc10th.domain.user.entity.mapping.UserTerms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermsRepository extends JpaRepository<UserTerms, Long> {
}
