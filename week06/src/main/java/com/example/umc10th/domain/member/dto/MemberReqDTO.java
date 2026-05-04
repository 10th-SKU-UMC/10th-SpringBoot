package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

public class MemberReqDTO {

    @Getter
    public static class SignupDTO {
        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(min = 2, max = 10, message = "닉네임은 2~10자 사이여야 합니다.")
        private String nickname;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        private String password;

        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (010-XXXX-XXXX)")
        private String phoneNumber;

        private Gender gender;
    }

    @Getter
    public static class LoginDTO {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }

    @Getter
    public static class SocialLoginDTO {
        @NotNull(message = "소셜 타입은 필수입니다.")
        private SocialType provider;

        @NotBlank(message = "소셜 ID는 필수입니다.")
        private String socialId;

        private String name;

        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;
    }

    @Getter
    public static class UpdateDTO {
        @Size(min = 2, max = 10, message = "닉네임은 2~10자 사이여야 합니다.")
        private String nickname;

        private String name;

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
        private String phoneNumber;

        private Gender gender;
    }

    @Getter
    public static class FoodPreferenceDTO {
        @NotEmpty(message = "음식 카테고리를 1개 이상 선택해주세요.")
        private List<Long> foodCategoryIds;
    }

    @Getter
    public static class AgreementDTO {
        @NotNull(message = "약관 타입 ID는 필수입니다.")
        private Long agreementTypeId;

        @NotNull(message = "동의 여부는 필수입니다.")
        private Boolean isAgreed;
    }

    @Getter
    public static class NotificationSettingDTO {
        private Boolean isMissionAlert;
        private Boolean isEventAlert;
        private Boolean isReviewAlert;
        private Boolean isInquiryAlert;
    }

    @Getter
    public static class TokenRefreshDTO {
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        private String refreshToken;
    }
}

