package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.apiPayload.code.JwtTokenProvider;
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    // SecurityConfig에서 등록한 인코더
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Query Parameter
    public String singleParameter(
            String singleParameter
    ){
        return singleParameter;
    }

    // Request Body
    public MemberResDTO.RequestBody requestBody(
            MemberReqDTO.RequestBody dto
    ){
        return MemberConverter.toRequestBody(dto.stringTest(), dto.longTest());
    }

    @Transactional
    // 원자성 보장
    public String createUser(
    ){
        Member member = Member.builder()
                .name("test")
                .build();
        memberRepository.save(member);
        return "OK";
    }

    @Transactional
    public String deleteUser(

    ){
        memberRepository.deleteByName("test");
        return "OK";
    }

    /*// 마이페이지 로직
    @Transactional
    public MemberResDTO.GetInfo getInfo(MemberReqDTO.GetInfo dto) {
        // DTO에서 유저 ID 추출
        Long memberId = dto.id();
        // DB에서 해당 유저 ID로 데이터 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        // 컨버터를 이용하여 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member);
    }*/
    // 마이페이지 로직 week09
    @Transactional
    public MemberResDTO.GetInfo getInfo(
            AuthMember member
    ){
        // 컨버터를 이용하여 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member.getMember());
    }

    /*// 로그인 로직
    @Transactional
    public MemberResDTO.LoginResDTO login(MemberReqDTO.LoginReqDTO request){
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        if (!member.getPassword().equals(request.password())){
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }
        String token = jwtTokenProvider.createToken(member.getEmail());
        return MemberConverter.toLoginResDTO(member, token);
    }*/

    // 로그인 로직 week09
    @Transactional
    public MemberResDTO.LoginResDTO login(MemberReqDTO.LoginReqDTO request){
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(request.password(), member.getPassword())){
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }
        String token = jwtTokenProvider.createToken(member.getEmail());
        return MemberConverter.toLoginResDTO(member, token);
    }


    // 회원가입 로직
    @Transactional
    public MemberResDTO.RegisterResDTO register(MemberReqDTO.RegisterReqDTO request){
        Member newMember = Member.builder()
                .name(request.name())
                .email(request.email())
                .nickname(request.nickname())
                .password(passwordEncoder.encode(request.password()))
                .gender(request.gender())
                .birth(request.birth())
                .phoneNumber(request.phoneNumber())
                .point(0)
                .build();

        Member savedMember = memberRepository.save(newMember);

        return MemberConverter.toRegisterResDTO(savedMember);
    }

    // week08 회원가입
    public Member joinMember(MemberReqDTO.JoinDTO request) {

        // 💡 request.getPassword()가 아니라 request.password()로 호출!
        String encodedPassword = passwordEncoder.encode(request.password());

        Member newMember = Member.builder()
                .name(request.name())
                .nickname(request.nickname())
                .email(request.email())
                .password(encodedPassword)
                .point(0)
                .build();

        return memberRepository.save(newMember);
    }
}
