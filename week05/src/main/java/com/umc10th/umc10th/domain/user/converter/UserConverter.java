package com.umc10th.umc10th.domain.user.converter;

import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.entity.Address;
import com.umc10th.umc10th.domain.user.entity.User;


public class UserConverter {

    public static UserResDto.ResponseBody toResponseBody(String stringTest, Long longTest) {
        return UserResDto.ResponseBody.builder()
                .stringTest(stringTest)
                .longTest(longTest)
                .build();
    }

    public static UserResDto.GetInfo toGetInfo(User user) {
        return UserResDto.GetInfo.builder()
                .name(user.getName())
                .profileUrl(user.getProfileUrl())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .point(user.getUserPoint() != null ? user.getUserPoint().getCurrent_total() : 0)
                .build();
    }

    //개 어렵다 그냥 미션만 해야겠다..
    public static User toUser(UserReqDto.SignUpRequestDto dto) {

        Address address = Address.builder()
                .address(dto.address())
                .specAddress(dto.specAddress())
                .zipcode(dto.zipcode())
                .build();

        return User.builder()
                .name(dto.name())
                .birth(dto.birth())
                .email(dto.email())
                .gender(dto.gender())
                .password(dto.password())
                .address(address)
                .build();

    }
}
