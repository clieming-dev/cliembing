package com.clb.cliembing.schedule.controller;

import com.clb.cliembing.schedule.dto.ScheduleManageDto;
import com.clb.cliembing.schedule.service.ScheduleManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name="일정 관리" , description=" 일정 검색 & 상세보기")
@RequestMapping("/api/schedules/manage")
@RequiredArgsConstructor
public class ScheduleManageRest {
    private final ScheduleManageService scheduleManageService;

    @GetMapping
    @Operation(
            summary = "일정을 검색할 수 있는 API",
            description = "스케줄 제목, 설명, 크루명, 암장명 등을 기준으로 일정을 검색할 수 있습니다."
    )
    public List<ScheduleManageDto.ScheduleSearchOutDto> searchSchedule(
            @ParameterObject ScheduleManageDto.ScheduleSearchInDto searchInDto
    ) {
        return scheduleManageService.searchSchedule(searchInDto);
    }

    @GetMapping("/detail")
    @Operation(
            summary = "일정 상세 정보를 조회하는 API",
            description = "일정 ID를 기반으로 상세 정보를 조회할 수 있습니다."
    )
    public ScheduleManageDto.ScheduleDetailOutDto getScheduleInfo(
            @Valid @ParameterObject ScheduleManageDto.SchduleDetailInDto detailInDto
    ) {
        return scheduleManageService.getScheduleInfo(detailInDto);
    }
}
