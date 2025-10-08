package com.clb.cliembing.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "크루 멤버 출석 현황 DTO")
public class CrewAttendanceStatusDto {

    @Schema(description = "크루 멤버 ID", example = "123")
    private Long crewMemberId;

    @Schema(description = "유저 내부 ID", example = "456")
    private Long userId;

    @Schema(description = "유저 로그인 아이디", example = "yoyo326")
    private String userLoginId;

    @Schema(description = "유저 이름", example = "Yoyo")
    private String userName;

    @Schema(description = "출석 일수", example = "15")
    private int attendanceCount;

    @Schema(description = "출석한 날짜 리스트", example = "[\"2025-10-01\", \"2025-10-02\", \"2025-10-03\"]")
    private List<LocalDate> attendanceDates;
}
