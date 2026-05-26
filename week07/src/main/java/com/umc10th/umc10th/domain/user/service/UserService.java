package com.umc10th.umc10th.domain.user.service;

import com.umc10th.umc10th.domain.mission.converter.MissionConverter;
import com.umc10th.umc10th.domain.mission.entity.Mission;
import com.umc10th.umc10th.domain.mission.repository.MissionRepository;
import com.umc10th.umc10th.domain.user.converter.UserConverter;
import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.exception.UserException;
import com.umc10th.umc10th.domain.user.exception.code.UserErrorCode;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public String createUser(UserReqDto.SignUpRequestDto dto) {

        User user = UserConverter.toUser(dto);
        userRepository.save(user);
        return null;
    }

    public String singleParameter(String singleParameter) {
            return singleParameter;
    }

    public UserResDto.ResponseBody requestBody(UserReqDto.RequestBody dto) {
        return UserConverter.toResponseBody(dto.stringTest(), dto.longTest());
    }

    public UserResDto.GetInfo getInfo(UserReqDto.getInfo dto) {

        User user = userRepository.findById(dto.id())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return UserConverter.toGetInfo(user);
    }


    public UserResDto.HomeViewDto getHomeView(UserReqDto.getHomeView request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        PageRequest pageable = PageRequest.of(request.page(), 10, Sort.by("createdAt").descending());

        Page<Mission> missionPage = missionRepository.findAllByRegionAndNotChallenged(request.userId(), request.regionName(), pageable);

        return UserConverter.toGetHomeViewDto(
                request.regionName(),
                user.getUserPoint().getCurrentTotal(),
                MissionConverter.toMissionListDto(missionPage)
        );
    }
}
