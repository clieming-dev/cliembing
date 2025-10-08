package com.clb.cliembing.schedule.repository;

import com.clb.cliembing.schedule.entity.ScheduleAttendeeEntity;
import com.clb.cliembing.schedule.entity.ScheduleEntity;
import com.clb.cliembing.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleAttendeeRepository extends JpaRepository<ScheduleAttendeeEntity,Long> {
    List<ScheduleAttendeeEntity> findBySchedule(ScheduleEntity schedule);

    List<ScheduleAttendeeEntity> findByUser(UserEntity user);

    List<ScheduleAttendeeEntity> findByScheduleAndUser(ScheduleEntity schedule, UserEntity user);

    List<ScheduleAttendeeEntity> findByIsDeletedFalse();

    boolean existsByScheduleAndUser(ScheduleEntity schedule, UserEntity user);

    //출석 현황 조회
    List<ScheduleAttendeeEntity> findByUser_IdInAndIsAttendingTrueAndSchedule_DateBetween(
            List<Long> userIds, LocalDate startDate, LocalDate endDate);

}
