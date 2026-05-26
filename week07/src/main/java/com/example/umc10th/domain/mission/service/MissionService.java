package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.mission.controller.MissionController;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.exception.code.StoreErrorCode;
import com.example.umc10th.domain.store.repository.StoreRepository;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 미션 목록 가져오기 로직
    @Transactional
    public MissionResDTO.ShowMissionListResDTO getMissionList(Long locationId){
        // 임시로 일단 전부 가져오는 로직
        List<Mission> missionList = missionRepository.findAll();
        return MissionConverter.toShowMissionListResDTO(missionList);
    }

    // 미션 성공 누르기
    @Transactional
    public void completeMission(Long userMissionId){
        MemberMission memberMission = memberMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new RuntimeException("해당 유저 미션이 존재하지 않습니다"));
        memberMission.complete();
    }

    // 사용자 미션 조회
    @Transactional
    public MissionResDTO.ShowMissionListResDTO getUserMissionList(Long userId, Integer isCompleteInt){
        Boolean isComplete = (isCompleteInt==1);
        List<MemberMission> memberMissionList = memberMissionRepository.findAllByMemberIdAndIsComplete(userId, isComplete);
        return MissionConverter.toGetUserMissionListResDTO(memberMissionList);
    }

    // 가게 미션 생성
    @Transactional
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMissionReqDTO dto
    ){
        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        // 미션 생성
        Mission newMission = MissionConverter.toCreateMission(store, dto);
        // 미션 DB 저장
        missionRepository.save(newMission);
        return null;
    }

    // 가게 내 미션 조회
    @Transactional
    public MissionResDTO.Pagination<MissionResDTO.GetMissionResDTO> getStoreMission(
            Long storeId,
            Integer pageSize,
            //Integer pageNumber,
            //String sort
            String cursor,
            String query
    ){
        /*// 가게 내 미션들 조회
        List<Mission> missionList = missionRepository.findAllByStore_Id(storeId);
        // 미션들 응답 DTO로 포장하기
        return missionList.stream()
                .map(MissionConverter::toGetStoreMission)
                .toList();*/
        /*// 정렬 정보 생성
        Sort sortInfo;
        if (sort != null){
            sortInfo = Sort.by(sort);
        } else{
            sortInfo = Sort.by("id").descending();
        }*/
        // 페이지 정보들을 PageRequest로 만들기
        /*PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortInfo);*/
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        /*// 가게 내 미션들 조회
        Page<Mission> missionList = missionRepository.findAllByStore_Id(storeId, pageRequest);*/

        long idCursor;
        Slice<Mission> missionList = null;
        String nextCursor;

        // 커서가 있는 경우
        if (!cursor.equals("-1")){
            // 커서 분리
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()){
                case "id":
                    // 커서 타입 변환
                    Long prevCursor = Long.parseLong(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);

                    // 가게 내 미션들 조회 & where절의 커서값 기입
                    missionList = missionRepository.findMissionByStore_IdAndIdLessThanOrderByIdDesc(
                            storeId,
                            idCursor,
                            pageRequest
                    );
                    break;
                default:
                    throw new MissionException(MissionErrorCode.QUERY_NOT_VALID);
            }
            // 커서 없이 조회
            missionList = missionRepository.findMissionByStore_IdOrderByIdDesc(storeId, pageRequest);
        }
        // 다음 커서 계산
        nextCursor = missionList.getContent().getLast().getId() + ":" + missionList.getContent().getLast().getId();

        //미션들 응답 DTO로 포장하기
        return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetStoreMission).toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize()
        );

        // 미션들 응답 DTO로 포장하기
        /*return missionList.map(MissionConverter::toGetStoreMission);*/
        /*return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetStoreMission).toList(),
                missionList.getNumber(),
                missionList.getSize()
        );*/
    }

    // 나의 미션 조회 로직
    @Transactional
    public Page<MemberMission> getMemberMissionList(
            MissionReqDTO.ShowMemberMissionReqDTO request,
            Integer page,
            Integer size
    ){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Boolean isComplete = (request.isComplete() == 1);
        return memberMissionRepository.findAllByMemberIdAndIsComplete(
                request.userId(),
                isComplete,
                pageRequest
        );
    }
}
