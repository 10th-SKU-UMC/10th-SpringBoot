package com.example.umc10th.member.dto;

import com.example.umc10th.member.enums.Gender;
import com.example.umc10th.mission.enums.Address;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record SignUpRequest(
            @NotBlank(message = "이름은 필수입니다.")
            @Size(max = 20, message = "이름은 20자 이하여야 합니다.")
            String name,

            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, max = 30, message = "비밀번호는 8자 이상 30자 이하여야 합니다.")
            String password,

            @NotBlank(message = "전화번호는 필수입니다.")
            @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 숫자 10~11자리여야 합니다.")
            String phoneNum,

            @NotBlank(message = "닉네임은 필수입니다.")
            @Size(min = 2, max = 15, message = "닉네임은 2자 이상 15자 이하여야 합니다.")
            String nickName,

            @NotNull(message = "성별은 필수입니다.")
            Gender gender,

            @NotNull(message = "생년월일은 필수입니다.")
            LocalDate birthDate,

            @NotNull(message = "주소는 필수입니다.")
            Address address,

            @Size(max = 50, message = "상세 주소는 50자 이하여야 합니다.")
            String detailAddress,

            @AssertTrue(message = "만 14세 이상 동의는 필수입니다.")
            boolean agreeAge,

            @AssertTrue(message = "서비스 이용약관 동의는 필수입니다.")
            boolean agreeService,

            @AssertTrue(message = "개인정보 처리 방침 동의는 필수입니다.")
            boolean agreePrivacy,

            boolean agreeLocation,

            boolean agreeMarketing,

            @Size(max = 20, message = "선호 음식은 최대 20개까지 선택 가능합니다.")
            List<@NotBlank(message = "선호 음식은 빈 값일 수 없습니다.") String> preferredFoods
    ) {}

    public record LoginRequest(
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            String password
    ) {}
}
