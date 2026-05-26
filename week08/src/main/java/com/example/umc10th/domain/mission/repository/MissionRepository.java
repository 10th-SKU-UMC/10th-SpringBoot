package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.store.entity.Store;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findAllByStoreId(Long storeId);

    List<Mission> findAllByStore_Id(Long storeId);

    Slice<Mission> findMissionByStore(Store store);

    Slice<Mission> findMissionByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, long idCursor, PageRequest pageRequest);

    Slice<Mission> findMissionByStore_IdOrderByIdDesc(Long storeId, PageRequest pageRequest);
}
