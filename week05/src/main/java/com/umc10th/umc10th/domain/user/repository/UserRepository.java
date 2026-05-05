package com.umc10th.umc10th.domain.user.repository;

import com.umc10th.umc10th.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByName(String name);
}
