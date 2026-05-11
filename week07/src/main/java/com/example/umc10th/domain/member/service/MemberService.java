package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.Term;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.exceptions.MemberException;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResDTO.MemberInfoDTO getMyProfile(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberConverter.toMemberInfoDTO(member);
    }

    @Transactional
    public MemberResDTO.MemberInfoDTO updateMyProfile(Long memberId, MemberReqDTO.UpdateDTO request) {
        Member member = findMemberById(memberId);
        member.updateProfile(request.getNickname(), request.getName(),
                request.getPhoneNumber(), request.getGender());
        return MemberConverter.toMemberInfoDTO(member);
    }

    public MemberResDTO.NotificationSettingDTO getNotificationSettings(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberConverter.toNotificationSettingDTO(member);
    }

    @Transactional
    public MemberResDTO.NotificationSettingDTO updateNotificationSettings(
            Long memberId, MemberReqDTO.NotificationSettingDTO request) {
        Member member = findMemberById(memberId);
        member.updateNotificationSettings(
                request.getIsMissionAlert(), request.getIsEventAlert(),
                request.getIsReviewAlert(), request.getIsInquiryAlert());
        return MemberConverter.toNotificationSettingDTO(member);
    }

    public MemberResDTO.AgreementListDTO getAgreements(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberConverter.toAgreementListDTO(member.getMemberTerms());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
