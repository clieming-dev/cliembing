package com.clb.cliembing.schedule.controller;

import com.clb.cliembing.schedule.dto.CrewAttendanceStatusDto;
import com.clb.cliembing.schedule.service.CrewAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/crew")
@RequiredArgsConstructor
@Tag(name = "크루 출석 관리", description = "크루 출석 현황 조회 API")
public class CrewAttendanceRest {

    private final CrewAttendanceService crewAttendanceService;

    @GetMapping("/{crewId}/attendance")
    @Operation(summary = "크루 출석 현황 조회", description = "크루 ID와 optional 날짜 범위로 출석 현황을 조회합니다. 날짜 미입력 시 오늘부터 31일 기간 조회")
    public List<CrewAttendanceStatusDto> getCrewAttendance(
            @PathVariable Long crewId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return crewAttendanceService.getAttendanceStatus(crewId, startDate, endDate);
    }
}
