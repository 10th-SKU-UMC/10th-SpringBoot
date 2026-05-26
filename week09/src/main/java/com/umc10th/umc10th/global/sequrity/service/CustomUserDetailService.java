package com.umc10th.umc10th.global.sequrity.service;

import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.enums.Provider;
import com.umc10th.umc10th.domain.user.exception.UserException;
import com.umc10th.umc10th.domain.user.exception.code.UserErrorCode;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import com.umc10th.umc10th.global.sequrity.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return new AuthUser(user);
    }

    // DB에서 기존 회원 정보 조회 & 인증 객체 생성
    public UserDetails loadUserByUidAndSocialType(
            Provider socialType,
            String username
    ) throws UsernameNotFoundException {

        User user = userRepository.findBySocialProviderAndSocialUid(socialType, username)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return new AuthUser(user);
    }
}
