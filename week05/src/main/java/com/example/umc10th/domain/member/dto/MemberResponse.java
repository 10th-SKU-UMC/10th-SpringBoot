package com.example.umc10th.domain.member.dto;

import lombok.Builder;

public class MemberResponse {

    @Builder
    public record RequestBody(
            String stringTest,
            Long longTest
    ){}

    @Builder
    public record GetInfo(String name, String profileUrl, String email, String phoneNumber, Integer point){}
}
