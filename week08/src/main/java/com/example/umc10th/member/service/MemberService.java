package com.example.umc10th.member.service;

import com.example.umc10th.member.converter.MemberConverter;
import com.example.umc10th.member.dto.MemberReqDTO;
import com.example.umc10th.member.dto.MemberResDTO;
import com.example.umc10th.member.entity.Member;
import com.example.umc10th.member.exception.MemberException;
import com.example.umc10th.member.exception.code.MemberErrorCode;
import com.example.umc10th.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
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
}
