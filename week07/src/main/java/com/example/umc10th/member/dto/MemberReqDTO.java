package com.example.umc10th.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MemberReqDTO {

    public record SignUpRequest(
            @NotBlank(message = "이름은 필수입니다.")
            @Size(max = 20, message = "이름은 20자 이하여야 합니다.")
            String name,

            @NotBlank(message = "전화번호는 필수입니다.")
            @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 숫자 10~11자리여야 합니다.")
            String phoneNum,

            @NotBlank(message = "닉네임은 필수입니다.")
            @Size(min = 2, max = 15, message = "닉네임은 2자 이상 15자 이하여야 합니다.")
            String nickName
    ) {}
}
