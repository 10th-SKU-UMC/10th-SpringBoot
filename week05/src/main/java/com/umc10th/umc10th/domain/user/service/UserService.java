package com.umc10th.umc10th.domain.user.service;

import com.umc10th.umc10th.domain.user.converter.UserConverter;
import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.exception.UserException;
import com.umc10th.umc10th.domain.user.exception.code.UserErrorCode;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public String createUser(UserReqDto.SignUpRequestDto dto) {

        User user = UserConverter.toUser(dto);
        userRepository.save(user);
        return null;
    }

    public String singleParameter(String singleParameter) {
            return singleParameter;
    }

    public UserResDto.ResponseBody requestBody(UserReqDto.RequestBody dto) {
        return UserConverter.toResponseBody(dto.stringTest(), dto.longTest());
    }

    public UserResDto.GetInfo getInfo(UserReqDto.getInfo dto) {

        User user = userRepository.findById(dto.id())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return UserConverter.toGetInfo(user);
    }


}
