package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupResultDTO {
        private Long userId;
        private String nickname;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO {
        private String accessToken;
        private String refreshToken;
        private MemberInfoDTO member;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialLoginResultDTO {
        private String accessToken;
        private String refreshToken;
        private MemberInfoDTO member;
        private Boolean isNewUser;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfoDTO {
        private Long userId;
        private String name;
        private String nickname;
        private String email;
        private String phoneNumber;
        private Gender gender;
        private Integer totalPoint;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointHistoryListDTO {
        private List<PointHistoryDTO> pointHistories;
        private Integer totalPoint;
        private Integer totalCount;
        private Integer currentPage;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointHistoryDTO {
        private Long pointHistoryId;
        private Integer amount;
        private String reason;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationSettingDTO {
        private Boolean isMissionAlert;
        private Boolean isEventAlert;
        private Boolean isReviewAlert;
        private Boolean isInquiryAlert;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AgreementListDTO {
        private List<AgreementDTO> agreements;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AgreementDTO {
        private Long agreementTypeId;
        private String agreementTypeName;
        private Boolean isAgreed;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime agreedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenRefreshResultDTO {
        private String accessToken;
    }
}
