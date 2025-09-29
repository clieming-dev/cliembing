package com.clb.cliembing.gym.repository;

import com.clb.cliembing.gym.entity.GymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymRepository extends JpaRepository<GymEntity, Long> {
    // 지역별 암장 조회
    List<GymEntity> findAllByRegionAndIsDeletedFalse(String region);

    // 혼잡도 기준 암장 조회
    List<GymEntity> findAllByCongestionLevelAndIsDeletedFalse(String congestionLevel);

    // 이름 키워드 검색
    List<GymEntity> findByGymNameContainingAndIsDeletedFalse(String keyword);

    // 주차 가능 암장
    List<GymEntity> findAllByHasParkingTrueAndIsDeletedFalse();

    // 특정 ID 조회 (Soft Delete 반영)
    GymEntity findByIdAndIsDeletedFalse(Long id);

}
