package com.clb.cliembing.schedule.repository;

import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.gym.entity.GymEntity;
import com.clb.cliembing.schedule.entity.ScheduleEntity;
import com.clb.cliembing.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    // 특정 유저의 스케줄 목록 조회
    List<ScheduleEntity> findByUser(UserEntity user);

    // 특정 크루의 스케줄 목록 조회
    List<ScheduleEntity> findByCrew(CrewEntity crew);

    // 특정 암장의 스케줄 목록 조회
    List<ScheduleEntity> findByGym(GymEntity gym);

    // 유저 + 타입으로 조회
    List<ScheduleEntity> findByUserAndScheduleType(UserEntity user, ScheduleEntity.ScheduleType scheduleType);

    // 크루 + 타입으로 조회
    List<ScheduleEntity> findByCrewAndScheduleType(CrewEntity crew, ScheduleEntity.ScheduleType scheduleType);

    // 삭제되지 않은 전체 스케줄
    List<ScheduleEntity> findByIsDeletedFalse();
}
