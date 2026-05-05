package com.umc10th.umc10th.domain.user.dto;

import com.umc10th.umc10th.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


public class UserReqDto {

    //record 이용
    public record RequestBody(
            String stringTest,
            Long longTest
    ) {}



    public record getInfo(
            Long id
    ){}

    //회원가입 dto
    //여기 전화번호 없나..?
    public record SignUpRequestDto(
            @NotBlank @Email String email,
            @NotBlank String password,
            @NotBlank String name,
            @NotNull Gender gender, // Enum 사용 권장
            @NotNull LocalDate birth, // LocalDate
            @NotBlank String address,
            @NotBlank String specAddress,
            @NotBlank String zipcode,

            // 약관 동의 관련
            List<TermAgreeDto> agreedTerms, // 동의한 필수/선택 약관 ID 리스트

            // 선호 카테고리
            List<Long> categories
    ) {}

    public record TermAgreeDto(
            Long termId,
            Boolean is_agreed
    ) {}

    public record loginRequestDto(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}

    public record updateProfileRequestDto(
            String name,
            String password,
            String phoneNumber,
            String address,
            String specAddress,
            String zipcode
    ) {}



}
