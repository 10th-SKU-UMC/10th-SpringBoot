package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberReqDTO {

    // Request Body 예시
    public record RequestBody(
            String stringTest,
            Long longTest
    ){}

    // public static class
    @Getter
    public static class RequestBodyClass{
        private String stringTest;
        private Long longTest;
    }

    // 마이페이지
    public record GetInfo(
            Long id
    ){}

    // 로그인 요청
    public record LoginReqDTO(
            String email,
            String password
    ){}

    // 회원 가입 요청
    public record RegisterReqDTO(
            String name,
            String email,
            String nickname,
            String password,
            Gender gender,
            LocalDate birth,
            String phoneNumber,
            LocalDateTime createdAt
    ){}
}
