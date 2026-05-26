package com.example.umc10th.member.converter;

import com.example.umc10th.member.dto.MemberReqDTO;
import com.example.umc10th.member.dto.MemberResDTO;
import com.example.umc10th.member.entity.Member;
import com.example.umc10th.mission.entity.mapping.MemberMission;
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

    public static MemberResDTO.GetInfo toGetInfo(Member member) {
        return new MemberResDTO.GetInfo(
                member.getName(),
                member.getProfileUrl(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getPoint()
        );
    }

    public static MemberResDTO.HomeResponse toHomeResponse(Member member, List<MemberMission> memberMissions) {
        List<MemberResDTO.HomeMissionSummary> missions = memberMissions.stream()
                .map(mm -> new MemberResDTO.HomeMissionSummary(
                        mm.getMission().getId(),
                        mm.getMission().getName(),
                        mm.getStatus().name()
                ))
                .toList();
        return new MemberResDTO.HomeResponse(member.getId(), member.getNickname(), missions);
    }

    private static Set<String> toPreferredFoods(List<String> preferredFoods) {
        if (preferredFoods == null || preferredFoods.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(preferredFoods);
    }
}
