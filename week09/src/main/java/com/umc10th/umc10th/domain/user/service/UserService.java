package com.umc10th.umc10th.domain.user.service;

import com.umc10th.umc10th.domain.category.entity.Category;
import com.umc10th.umc10th.domain.category.repository.CategoryRepository;
import com.umc10th.umc10th.domain.mission.converter.MissionConverter;
import com.umc10th.umc10th.domain.mission.entity.Mission;
import com.umc10th.umc10th.domain.mission.repository.MissionRepository;
import com.umc10th.umc10th.domain.term.entity.Term;
import com.umc10th.umc10th.domain.term.repository.TermRepository;
import com.umc10th.umc10th.domain.user.converter.UserConverter;
import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.entity.mapping.UserCategory;
import com.umc10th.umc10th.domain.user.entity.mapping.UserTerms;
import com.umc10th.umc10th.domain.user.exception.UserException;
import com.umc10th.umc10th.domain.user.exception.code.UserErrorCode;
import com.umc10th.umc10th.domain.user.repository.UserCategoryRepository;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import com.umc10th.umc10th.domain.user.repository.UserTermsRepository;
import com.umc10th.umc10th.global.sequrity.entity.AuthUser;
import com.umc10th.umc10th.global.sequrity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CategoryRepository categoryRepository;
    private final TermRepository termRepository;
    private final UserTermsRepository userTermsRepository;
    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    public UserResDto.SignUpResponseDto createUser(UserReqDto.SignUpRequestDto dto) {


        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = UserConverter.toUser(dto, encodedPassword);
        userRepository.save(user);

        if (dto.agreedTerms() != null) {
            for (UserReqDto.TermAgreeDto termAgreeDto : dto.agreedTerms()) {
                Term term = termRepository.findById(termAgreeDto.termId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 약관입니다: " + termAgreeDto.termId()));
                UserTerms userTerms = UserTerms.builder()
                        .user(user)
                        .term(term)
                        .isAgreed(termAgreeDto.is_agreed())
                        .build();
                userTermsRepository.save(userTerms);
            }
        }

        if (dto.categories() != null) {
            for (Long categoryId : dto.categories()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다: " + categoryId));
                UserCategory userCategory = UserCategory.builder()
                        .user(user)
                        .category(category)
                        .build();
                userCategoryRepository.save(userCategory);
            }
        }

        return UserResDto.SignUpResponseDto.builder()
                .userId(user.getId())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public String singleParameter(String singleParameter) {
            return singleParameter;
    }

    public UserResDto.ResponseBody requestBody(UserReqDto.RequestBody dto) {
        return UserConverter.toResponseBody(dto.stringTest(), dto.longTest());
    }

    public UserResDto.LoginResultDto login(UserReqDto.loginRequestDto dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.createAccessToken(new AuthUser(user));

        return UserResDto.LoginResultDto.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .build();
    }

    public UserResDto.GetInfo getInfo(User user) {
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
