package com.clb.cliembing.schedule.service;

import com.clb.cliembing.schedule.dto.ScheduleManageDto;
import com.clb.cliembing.schedule.entity.ScheduleEntity;
import com.clb.cliembing.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleManageService {

    private final ScheduleRepository scheduleRepository;

    // List로 응답
    public List<ScheduleManageDto.ScheduleSearchOutDto> searchSchedule(ScheduleManageDto.ScheduleSearchInDto inDto) {

        List<ScheduleManageDto.ScheduleSearchOutDto> content = new ArrayList<>();

        // 🧪 MOCK 데이터 예시 (추후 DB 연동)
        ScheduleManageDto.ScheduleSearchOutDto dto = ScheduleManageDto.ScheduleSearchOutDto.builder()
                .scheduleId(1L)
                .title("주말 클라이밍 번개")
                .description("홍대 더클라임에서 클라이밍 모임")
                .startTime(java.sql.Timestamp.valueOf("2025-09-28 10:00:00"))
                .endTime(java.sql.Timestamp.valueOf("2025-09-28 13:00:00"))
                .location("더클라임 홍대점")
                .scheduleType("crew")
                .crewName("서울 클라이밍")
                .createdBy("Yoyo")
                .build();

        content.add(dto);

        return content;
    }

    public ScheduleManageDto.ScheduleDetailOutDto getScheduleInfo(ScheduleManageDto.SchduleDetailInDto inDto) {
        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(inDto.getScheduleId());

        if (scheduleEntity.isEmpty()) {
            throw new NoSuchElementException("요청하신 ScheduleID가 존재하지 않습니다.");
        }

        return ScheduleManageDto.ScheduleDetailOutDto.fromEntity(scheduleEntity.get());
    }
}
