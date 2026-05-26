package com.example.umc10th.member.service;

import com.example.umc10th.member.converter.MemberConverter;
import com.example.umc10th.member.dto.MemberReqDTO;
import com.example.umc10th.member.dto.MemberResDTO;
import com.example.umc10th.member.entity.Member;
import com.example.umc10th.member.exception.MemberException;
import com.example.umc10th.member.exception.code.MemberErrorCode;
import com.example.umc10th.member.repository.MemberRepository;
import com.example.umc10th.global.security.AuthMember;
import com.example.umc10th.global.security.jwt.JwtTokenProvider;
import com.example.umc10th.mission.entity.mapping.MemberMission;
import com.example.umc10th.mission.repository.MemberMissionRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMissionRepository memberMissionRepository;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, MemberMissionRepository memberMissionRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberMissionRepository = memberMissionRepository;
    }

    @Transactional
    public MemberResDTO.SignUpResponse signUp(MemberReqDTO.SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.MEMBER_DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = MemberConverter.toEntity(request, encodedPassword);
        Member savedMember = memberRepository.save(member);

        return MemberConverter.toSignUpResponse(savedMember);
    }

    @Transactional(readOnly = true)
    public MemberResDTO.LoginResponse login(MemberReqDTO.LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.MEMBER_INVALID_CREDENTIALS);
        }

        String accessToken = jwtTokenProvider.createAccessToken(member);
        return new MemberResDTO.LoginResponse(accessToken, "Bearer");
    }

    @Transactional(readOnly = true)
    public MemberResDTO.GetInfo getInfo(AuthMember authMember) {
        return MemberConverter.toGetInfo(authMember.getMember());
    }

    @Transactional(readOnly = true)
    public MemberResDTO.HomeResponse getHome(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        List<MemberMission> memberMissions = memberMissionRepository.findAllByMemberId(userId);
        return MemberConverter.toHomeResponse(member, memberMissions);
    }
}
