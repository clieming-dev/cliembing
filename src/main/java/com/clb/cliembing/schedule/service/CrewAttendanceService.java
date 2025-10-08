package com.clb.cliembing.schedule.service;

import com.clb.cliembing.schedule.dto.CrewAttendanceStatusDto;
import com.clb.cliembing.schedule.entity.ScheduleAttendeeEntity;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.crew.entity.CrewMemberEntity;
import com.clb.cliembing.crew.repository.CrewMemberRepository;
import com.clb.cliembing.crew.repository.CrewRepository;
import com.clb.cliembing.schedule.repository.ScheduleAttendeeRepository;
import com.clb.cliembing.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrewAttendanceService {

    private final CrewRepository crewRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final ScheduleAttendeeRepository scheduleAttendeeRepository;

    /**
     * 크루 출석 현황 조회
     * @param crewId 크루 아이디
     * @param startDate 조회 시작일 (null이면 오늘)
     * @param endDate 조회 종료일 (null이면 오늘부터 31일 후)
     * @return 크루별 출석 상태 리스트
     */
    public List<CrewAttendanceStatusDto> getAttendanceStatus(Long crewId, LocalDate startDate, LocalDate endDate) {
        // 기본 날짜 세팅: startDate = 오늘, endDate = 오늘+30일 (총 31일)
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = startDate.plusDays(30);
        }

        // 기간 제한: 최대 31일
        if (startDate.plusDays(31).isBefore(endDate)) {
            throw new IllegalArgumentException("조회 기간은 최대 31일까지 가능합니다.");
        }

        // 크루 조회
        CrewEntity crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new IllegalArgumentException("크루가 존재하지 않습니다."));

        // 크루 멤버 전체 조회
        List<CrewMemberEntity> members = crewMemberRepository.findByCrew_Id(crewId);

        // 크루 멤버 유저 ID 리스트
        List<Long> userIds = members.stream()
                .map(m -> m.getUser().getId())
                .collect(Collectors.toList());

        // 출석 기록 조회 (기간 내, 해당 유저만)
        List<ScheduleAttendeeEntity> attendanceList = scheduleAttendeeRepository
                .findByUser_IdInAndIsAttendingTrueAndSchedule_DateBetween(userIds, startDate, endDate);

        // userId 기준으로 참석 날짜 리스트 생성
        Map<Long, List<LocalDate>> userAttendanceMap = attendanceList.stream()
                .collect(Collectors.groupingBy(
                        sa -> sa.getUser().getId(),
                        Collectors.mapping(sa -> sa.getSchedule().getDate(), Collectors.toList())
                ));

        // 결과 매핑 및 정렬(이름 가나다 순)
        return members.stream()
                .map(member -> {
                    UserEntity user = member.getUser();
                    List<LocalDate> dates = userAttendanceMap.getOrDefault(user.getId(), Collections.emptyList());

                    return CrewAttendanceStatusDto.builder()
                            .crewMemberId(member.getId())
                            .userId(user.getId())
                            .userLoginId(user.getUserId())
                            .userName(user.getUserName())
                            .attendanceCount(dates.size())
                            .attendanceDates(dates)
                            .build();
                })
                .sorted(Comparator.comparing(CrewAttendanceStatusDto::getUserName))
                .collect(Collectors.toList());
    }
}
