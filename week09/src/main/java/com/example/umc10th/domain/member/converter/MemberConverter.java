package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    public static MemberResDTO.LoginResultDTO toLoginResultDTO(
            String accessToken, String refreshToken, Member member) {
        return MemberResDTO.LoginResultDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .member(toMemberInfoDTO(member))
                .build();
    }

    public static MemberResDTO.SignupResultDTO toSignupResultDTO(Member member) {
        return MemberResDTO.SignupResultDTO.builder()
                .userId(member.getId())
                .nickname(member.getNickname())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.MemberInfoDTO toMemberInfoDTO(Member member) {
        return MemberResDTO.MemberInfoDTO.builder()
                .userId(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender())
                .totalPoint(member.getPoint())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.NotificationSettingDTO toNotificationSettingDTO(Member member) {
        return MemberResDTO.NotificationSettingDTO.builder()
                .isMissionAlert(member.getMissionAlert())
                .isEventAlert(member.getEventAlert())
                .isReviewAlert(member.getReviewAlert())
                .isInquiryAlert(member.getInquiryAlert())
                .build();
    }

    public static MemberResDTO.AgreementDTO toAgreementDTO(MemberTerm memberTerm) {
        Term term = memberTerm.getTerm();
        return MemberResDTO.AgreementDTO.builder()
                .agreementTypeId(term.getId())
                .agreementTypeName(term.getTitle())
                .isAgreed(memberTerm.getIsAgreed())
                .agreedAt(memberTerm.getAgreedAt())
                .build();
    }

    public static MemberResDTO.AgreementListDTO toAgreementListDTO(List<MemberTerm> memberTerms) {
        List<MemberResDTO.AgreementDTO> agreements = memberTerms.stream()
                .map(MemberConverter::toAgreementDTO)
                .collect(Collectors.toList());
        return MemberResDTO.AgreementListDTO.builder()
                .agreements(agreements)
                .build();
    }
}
