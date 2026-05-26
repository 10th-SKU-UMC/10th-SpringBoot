package com.umc10th.umc10th.domain.user.converter;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.entity.Address;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.enums.Gender;
import com.umc10th.umc10th.global.sequrity.dto.OAuthDTO;

import java.time.LocalDate;


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
                .point(user.getUserPoint() != null ? user.getUserPoint().getCurrentTotal() : 0)
                .build();
    }

    public static User toUser(UserReqDto.SignUpRequestDto dto, String encodedPassword) {

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
                .password(encodedPassword)
                .address(address)
                .build();

    }


    public static User toUser(OAuthDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getSocialEmail())
                .socialProvider(dto.getSocialType())
                .socialUid(dto.getSocialUid())
                .password("") // OAuth 사용자는 비밀번호 없음
                .birth(LocalDate.of(1900, 1, 1)) // OAuth 유저 기본값
                .gender(Gender.MALE) // OAuth 유저 기본값
                .build();
    }

    public static UserResDto.HomeViewDto toGetHomeViewDto(
            String regionName, Integer totalPoint, MissionResDto.MissionListDto missionList) {

        return UserResDto.HomeViewDto.builder()
                .regionName(regionName)
                .totalPoint(totalPoint)
                .missionList(missionList)
                .build();
    }
}
