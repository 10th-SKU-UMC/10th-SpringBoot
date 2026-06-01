package com.example.umc10th.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder
    public record RequestBody(
            String stringTest,
            Long longTest
    ){}

    @Builder
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point
    ){}

    // 로그인 응답
    @Builder
    public record LoginResDTO(
            String token
    ){}

    // 인증 로그인 응답
    @Builder
    public record Login(
            String accessToken
    ){}

    // 인증 마이페이지 응답 week09
    @Builder
    public record MyPageResDTO(
            String name,
            String email,
            Integer point,
            String phoneNumber,
            String socialType
    ){}

    // 회원 가입 응답
    @Builder
    public record RegisterResDTO(
            Long memberId,
            LocalDateTime createdAt
    ){}

    // week08 회원가입
    @Builder
    public record JoinResultDTO(
            Long memberId,
            LocalDateTime createdAt
    ) {}
}
