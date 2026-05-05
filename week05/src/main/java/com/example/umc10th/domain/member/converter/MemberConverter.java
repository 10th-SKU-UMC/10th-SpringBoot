package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResponse;
import com.example.umc10th.domain.member.entity.Member;

public class MemberConverter {

    public static MemberResponse.RequestBody toRequestBody(
            String stringTest,
            Long longTest
    ) {
        return MemberResponse.RequestBody.builder()
                .stringTest(stringTest)
                .longTest(longTest)
                .build();
    }

    public static MemberResponse.GetInfo toGetInfo(Member member) {
        return MemberResponse.GetInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .build();
    }
}
