package com.umc10th.umc10th.domain.user.repository;

import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findBySocialProviderAndSocialUid(Provider socialProvider, String socialUid);
}
