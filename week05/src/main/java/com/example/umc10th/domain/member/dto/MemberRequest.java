package com.example.umc10th.domain.member.dto;

public class MemberRequest {

    public record RequestBody(
            String stringTest,
            Long longTest
    ) {}

    // 마이페이지
    public record GetInfo(
            Long id
    ){}
}
