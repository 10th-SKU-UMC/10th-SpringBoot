package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.member.exceptions.MemberException;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.apiPayload.code.ErrorCode;
import com.example.umc10th.global.jwt.JwtTokenProvider;
import com.example.umc10th.global.jwt.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenStore refreshTokenStore;

    /* ========== 인증 ========== */

    @Transactional
    public MemberResDTO.SignupResultDTO signup(MemberReqDTO.SignupDTO request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new MemberException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .name(request.getName())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .socialType(SocialType.NONE)
                .build();

        return MemberConverter.toSignupResultDTO(memberRepository.save(member));
    }

    @Transactional
    public MemberResDTO.LoginResultDTO login(MemberReqDTO.LoginDTO request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getEmail());
        refreshTokenStore.save(member.getEmail(), refreshToken);

        return MemberConverter.toLoginResultDTO(accessToken, refreshToken, member);
    }

    @Transactional
    public void logout(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new MemberException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(refreshToken);
        refreshTokenStore.delete(email);
    }

    public MemberResDTO.TokenRefreshResultDTO refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new MemberException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(refreshToken);
        if (!refreshTokenStore.isValid(email, refreshToken)) {
            throw new MemberException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String newAccessToken = jwtTokenProvider.generateAccessToken(email);
        return MemberResDTO.TokenRefreshResultDTO.builder()
                .accessToken(newAccessToken)
                .build();
    }

    @Transactional
    public void withdraw(Long memberId) {
        Member member = findMemberById(memberId);
        refreshTokenStore.delete(member.getEmail());
        memberRepository.delete(member);
    }

    /* ========== 마이페이지 ========== */

    public MemberResDTO.MemberInfoDTO getMyProfile(Long memberId) {
        return MemberConverter.toMemberInfoDTO(findMemberById(memberId));
    }

    @Transactional
    public MemberResDTO.MemberInfoDTO updateMyProfile(Long memberId, MemberReqDTO.UpdateDTO request) {
        Member member = findMemberById(memberId);
        member.updateProfile(request.getNickname(), request.getName(),
                request.getPhoneNumber(), request.getGender());
        return MemberConverter.toMemberInfoDTO(member);
    }

    public MemberResDTO.NotificationSettingDTO getNotificationSettings(Long memberId) {
        return MemberConverter.toNotificationSettingDTO(findMemberById(memberId));
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
        return MemberConverter.toAgreementListDTO(findMemberById(memberId).getMemberTerms());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
