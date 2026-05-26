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
    
    // 회원 가입 응답
    public record RegisterResDTO(){}

    // week08 회원가입
    public record JoinResultDTO(
            Long memberId,
            LocalDateTime createdAt
    ) {}
}
