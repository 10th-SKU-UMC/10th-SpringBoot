package com.example.umc10th.member.converter;

import com.example.umc10th.member.dto.MemberReqDTO;
import com.example.umc10th.member.dto.MemberResDTO;
import com.example.umc10th.member.entity.Member;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MemberConverter {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private MemberConverter() {
    }

    public static Member toEntity(MemberReqDTO.SignUpRequest request, String encodedPassword) {
        Set<String> preferredFoods = toPreferredFoods(request.preferredFoods());

        return new Member(
                request.name(),
                request.email(),
                encodedPassword,
                request.nickName(),
                request.phoneNum(),
                request.gender(),
                request.birthDate(),
                request.address(),
                request.detailAddress(),
                request.agreeAge(),
                request.agreeService(),
                request.agreePrivacy(),
                request.agreeLocation(),
                request.agreeMarketing(),
                preferredFoods,
                DEFAULT_ROLE,
                LocalDateTime.now()
        );
    }

    public static MemberResDTO.SignUpResponse toSignUpResponse(Member member) {
        List<String> preferredFoods = member.getPreferredFoods() == null
                ? List.of()
                : member.getPreferredFoods().stream().sorted().toList();

        return new MemberResDTO.SignUpResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getNickname(),
                member.getGender(),
                member.getBirthDate(),
                member.getAddress(),
                member.getDetailAddress(),
                preferredFoods
        );
    }

    private static Set<String> toPreferredFoods(List<String> preferredFoods) {
        if (preferredFoods == null || preferredFoods.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(preferredFoods);
    }
}
