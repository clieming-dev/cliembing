package com.clb.cliembing.schedule.service;

import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.crew.repository.CrewRepository;
import com.clb.cliembing.gym.entity.GymEntity;
import com.clb.cliembing.gym.repository.GymRepository;
import com.clb.cliembing.schedule.dto.ScheduleManageDto;
import com.clb.cliembing.schedule.entity.ScheduleEntity;
import com.clb.cliembing.schedule.repository.ScheduleRepository;
import com.clb.cliembing.user.entity.UserEntity;
import com.clb.cliembing.user.repository.UserRepository;
import jakarta.validation.Valid;
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
    private final CrewRepository crewRepository;
    private final UserRepository userRepository;
    private final GymRepository gymRepository;

    // List로 응답
    public List<ScheduleManageDto.ScheduleSearchOutDto> searchSchedule(ScheduleManageDto.ScheduleSearchInDto inDto) {

        List<ScheduleManageDto.ScheduleSearchOutDto> content = new ArrayList<>();

        // 🧪 MOCK 데이터 예시 (추후 DB 연동)
        ScheduleManageDto.ScheduleSearchOutDto dto = ScheduleManageDto.ScheduleSearchOutDto.builder()
                .id(1L)
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
        Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findById(inDto.getId());

        if (scheduleEntity.isEmpty()) {
            throw new NoSuchElementException("요청하신 ScheduleID가 존재하지 않습니다.");
        }

        return ScheduleManageDto.ScheduleDetailOutDto.fromEntity(scheduleEntity.get());
    }

    public ScheduleManageDto.ScheduleAttendOutDto attendSchedule(ScheduleManageDto.ScheduleAttendInDto inDto) {
        // TODO: 실제 참석 여부를 저장할 DB 처리 필요

        String message = inDto.getAttend() ? "참석이 등록되었습니다." : "불참이 등록되었습니다.";

        return ScheduleManageDto.ScheduleAttendOutDto.builder()
                .message(message)
                .build();
    }

    public ScheduleManageDto.ScheduleCreateOutDto createSchedule(ScheduleManageDto.@Valid ScheduleCreateInDto dto) {

        //  필요한 Entity 조회
        CrewEntity crew = crewRepository.findById(dto.getCrewId())
                .orElseThrow(() -> new NoSuchElementException("크루 정보 없음"));

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("유저 정보 없음"));

        GymEntity gym = gymRepository.findById(dto.getGymId())
                .orElseThrow(() -> new NoSuchElementException("암장 정보 없음"));


        // 1. ScheduleEntity로 변환
        ScheduleEntity entity = ScheduleEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .location(dto.getLocation())
                .scheduleType(ScheduleEntity.ScheduleType.valueOf(dto.getScheduleType().toUpperCase()))
                .crew(crew)
                .user(user)
                .gym(gym)
                .isDeleted(false)
                .build();

        // 2. 저장
        ScheduleEntity saved = scheduleRepository.save(entity);

        // 3. 응답 생성
        return ScheduleManageDto.ScheduleCreateOutDto.builder()
                .scheduleId(saved.getId())
                .message("일정이 성공적으로 생성되었습니다.")
                .build();
    }
}
