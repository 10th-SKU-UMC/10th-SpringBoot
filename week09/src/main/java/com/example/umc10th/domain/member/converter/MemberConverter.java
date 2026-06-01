package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.global.security.dto.OAuthDTO;

import java.time.LocalDateTime;

public class MemberConverter {

    public static MemberResDTO.RequestBody toRequestBody(
            String stringTest,
            Long longTest
    ){
        return MemberResDTO.RequestBody.builder()
                .stringTest(stringTest)
                .longTest(longTest)
                .build();
    }

    // 마이페이지 반환
    public static MemberResDTO.GetInfo toGetInfo(Member member) {
        return MemberResDTO.GetInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

    // 로그인 반환
    public static MemberResDTO.LoginResDTO toLoginResDTO(Member member, String token){
        return MemberResDTO.LoginResDTO.builder()
                .token(token)   // 생성된 토큰 주입
                .build();
    }

    public static MemberResDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return new MemberResDTO.JoinResultDTO(
                member.getId(),
                member.getCreatedAt()
        );
    }

    // 회원 가입 응답 변환
    public static MemberResDTO.RegisterResDTO toRegisterResDTO(Member member) {
        return MemberResDTO.RegisterResDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 인증 마이페이지 응답
    public static MemberResDTO.MyPageResDTO toMyPageResDTO(Member member) {
        return MemberResDTO.MyPageResDTO.builder()
                .name(member.getName())
                .email(member.getEmail())
                .point(member.getPoint())
                .socialType(member.getSocialType() != null ? member.getSocialType().name() : "일반회원")
                .build();
    }

    // OAuthDTO로 Member 생성하기
    public static Member toMember(OAuthDTO dto){
        return Member.builder()
                .socialUid(dto.getSocialUid())
                .socialType(dto.getSocialType())
                .email(dto.getSocialEmail())
                .name(dto.getName())
                .build();
    }

    public static MemberResDTO.Login toLogin(String accessToken){
        return MemberResDTO.Login.builder()
                .accessToken(accessToken)
                .build();
    }
}
