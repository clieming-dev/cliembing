package com.clb.cliembing.crew.repository;

import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.crew.entity.CrewMemberEntity;
import com.clb.cliembing.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMemberEntity, Long> {

    // 특정 크루에 속한 모든 멤버 조회
    List<CrewMemberEntity> findByCrew(CrewEntity crew);

    // 특정 유저가 속한 모든 크루멤버 정보 조회
    List<CrewMemberEntity> findByUser(UserEntity user);

    // 유저 ID로 조회
    List<CrewMemberEntity> findByUser_Id(Long userId);

    // 크루 ID로 조회
    List<CrewMemberEntity> findByCrew_Id(Long crewId);

    // 특정 유저가 특정 크루에 속해 있는지
    Optional<CrewMemberEntity> findByCrewAndUser(CrewEntity crew, UserEntity user);

    // 역할(role) 기반 조회
    List<CrewMemberEntity> findByRole(String role);
}
