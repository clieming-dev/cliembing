package com.clb.cliembing.crew.repository;

import com.clb.cliembing.crew.entity.CrewApplicationEntity;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrewApplicationRepository extends JpaRepository<CrewApplicationEntity, Long> {
    // 신청자 + 크루로 단건 조회 (예: 중복 신청 방지 등)
    Optional<CrewApplicationEntity> findByCrewAndUserAndIsDeletedFalse(CrewEntity crew, UserEntity user);

    // 특정 크루에 대한 전체 신청자 리스트
    List<CrewApplicationEntity> findAllByCrewAndIsDeletedFalse(CrewEntity crew);

    // 특정 사용자 기준 신청 내역
    List<CrewApplicationEntity> findAllByUserAndIsDeletedFalse(UserEntity user);

    // 상태 기준 필터링
    List<CrewApplicationEntity> findAllByCrewAndStatusAndIsDeletedFalse(CrewEntity crew, String status);
}
