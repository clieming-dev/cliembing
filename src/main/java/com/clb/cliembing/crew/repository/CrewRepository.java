package com.clb.cliembing.crew.repository;

import com.clb.cliembing.crew.entity.CrewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrewRepository extends JpaRepository<CrewEntity, Long> {
    // crewName 으로 조회
    Optional<CrewEntity> findByCrewName(String crewName);

    // 공개된 크루만 조회
    List<CrewEntity> findByIsPublicTrue();

    // 특정 지역의 크루 리스트
    List<CrewEntity> findByRegion(String region);

    // 특정 유저가 만든 크루 리스트 (owner.id 기반)
    List<CrewEntity> findByOwner_Id(Long ownerId);

    // 모집 상태로 조회
    List<CrewEntity> findByRecruitmentStatus(String recruitmentStatus);

    // 활동 점수가 특정 값 이상인 크루 조회
    List<CrewEntity> findByActivityScoreGreaterThanEqual(Float score);

}
