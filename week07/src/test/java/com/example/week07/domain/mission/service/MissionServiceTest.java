package com.example.week07.domain.mission.service;

import com.example.week07.domain.mission.dto.MissionReqDTO;
import com.example.week07.domain.mission.dto.MissionResDTO;
import com.example.week07.domain.mission.entity.Mission;
import com.example.week07.domain.mission.entity.Store;
import com.example.week07.domain.mission.entity.mapping.MemberMission;
import com.example.week07.domain.mission.exception.StoreException;
import com.example.week07.domain.mission.repository.MemberMissionRepository;
import com.example.week07.domain.mission.repository.MissionRepository;
import com.example.week07.domain.mission.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    @Mock MemberMissionRepository memberMissionRepository;
    @Mock MissionRepository missionRepository;
    @Mock StoreRepository storeRepository;

    @InjectMocks MissionService missionService;

    // 테스트용 가게 엔티티 생성 헬퍼
    private Store createStore(String name) {
        return Store.builder()
                .name(name)
                .managerNumber(1L)
                .detailAddress("서울시 강남구")
                .build();
    }

    // 테스트용 미션 엔티티 생성 헬퍼
    private Mission createMission(Store store) {
        return Mission.builder()
                .conditional("12,000원 이상 식사")
                .point(500)
                .deadline(LocalDate.now().plusDays(7))
                .store(store)
                .build();
    }

    @Test
    @DisplayName("가게 미션 생성 - 성공")
    void createMission_성공() {
        // given
        Long storeId = 1L;
        Store store = createStore("테스트 가게");
        MissionReqDTO.CreateMission dto = new MissionReqDTO.CreateMission(
                LocalDate.now().plusDays(7), 500, "12,000원 이상 식사"
        );
        given(storeRepository.findById(storeId)).willReturn(Optional.of(store));
        given(missionRepository.save(any(Mission.class))).willAnswer(inv -> inv.getArgument(0));

        // when
        missionService.createMission(storeId, dto);

        // then - save가 호출되었는지 검증
        verify(missionRepository).save(any(Mission.class));
    }

    @Test
    @DisplayName("가게 미션 생성 - 존재하지 않는 가게면 StoreException 발생")
    void createMission_가게없음_예외발생() {
        // given
        Long storeId = 999L;
        MissionReqDTO.CreateMission dto = new MissionReqDTO.CreateMission(
                LocalDate.now().plusDays(7), 500, "12,000원 이상 식사"
        );
        given(storeRepository.findById(storeId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> missionService.createMission(storeId, dto))
                .isInstanceOf(StoreException.class);
    }

    @Test
    @DisplayName("가게 내 미션 목록 조회 - 성공")
    void getStoreMissions_성공() {
        // given
        Long storeId = 1L;
        Store store = createStore("테스트 가게");
        Mission mission = createMission(store);
        given(missionRepository.findAllByStore_Id(storeId)).willReturn(List.of(mission));

        // when
        List<MissionResDTO.GetMission> result = missionService.getMissions(storeId);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).conditional()).isEqualTo("12,000원 이상 식사");
        assertThat(result.get(0).point()).isEqualTo(500);
    }

    @Test
    @DisplayName("가게 내 미션 목록 조회 - 미션이 없으면 빈 리스트 반환")
    void getStoreMissions_미션없음_빈리스트반환() {
        // given
        Long storeId = 1L;
        given(missionRepository.findAllByStore_Id(storeId)).willReturn(List.of());

        // when
        List<MissionResDTO.GetMission> result = missionService.getMissions(storeId);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("내 미션 목록 조회 - ongoing 상태 조회 성공")
    void getMemberMissions_ongoing_성공() {
        // given
        Long memberId = 1L;
        // MemberMission은 연관 엔티티가 많아 mock으로 처리
        MemberMission memberMission = mock(MemberMission.class);
        Mission mission = createMission(createStore("테스트 가게"));
        when(memberMission.getMission()).thenReturn(mission);
        when(memberMission.getIsComplete()).thenReturn(false);

        PageImpl<MemberMission> page = new PageImpl<>(
                List.of(memberMission), PageRequest.of(0, 10), 1
        );
        given(memberMissionRepository.findByMemberIdAndIsComplete(memberId, false, PageRequest.of(0, 10)))
                .willReturn(page);

        // when
        MissionResDTO.MissionListRes result = missionService.getMissions(memberId, "ongoing", 0, 10);

        // then
        assertThat(result.missions()).hasSize(1);
        assertThat(result.totalCount()).isEqualTo(1);
        assertThat(result.hasNext()).isFalse();
    }
}
