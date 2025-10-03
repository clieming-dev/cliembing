package com.clb.cliembing.schedule.dto;

import com.clb.cliembing.schedule.entity.ScheduleEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.sql.Timestamp;

@Getter
public class ScheduleManageDto {

    @Getter
    public static class ScheduleSearchInDto{

        @Schema(description = "검색 키워드(제목, 설명 등)", example = "서울 클라이밍 일정" ,maxLength = 50 )
        private String keyword;

        @Schema(description = "크루 ID로 필터링", example = "101")
        private Long crewId;

        @Schema(description = "암장 ID로 필터링", example = "10")
        private Long gymId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "일정 검색 결과 DTO")
    public static class ScheduleSearchOutDto{

        @Schema(description = "일정ID", example = "1")
        private Long id;

        @Schema(description = "제목",example = "주말 클라이밍 번개", maxLength = 100)
        private String title;

        @Schema(description = "설명", example = "서울 암장에서 클라이밍 모임", maxLength = 500 )
        private String description;

        @Schema(description = "시작 시간", example = "2025-09-25T10:00:00")
        private Timestamp startTime;

        @Schema(description = "종료 시간", example = "2025-09-25T13:00:00")
        private Timestamp endTime;

        @Schema(description = "장소", example = "서울숲 볼더링 암장", maxLength = 100)
        private String location;

        @Schema(description = "스케줄 타입", example = "crew", allowableValues = {"crew", "personal"})
        private String scheduleType;

        @Schema(description = "크루 이름", example = "YoyoCrew")
        private String crewName;

        @Schema(description = "작성자 이름", example = "Yoyo")
        private String createdBy;
    }

    @Builder
    @Getter
    public static class SchduleDetailInDto{

        @Schema(description = "일정 ID", example = "1")
        @NotNull
        @Positive
        private Long id;
    }

    @Data
    @Builder
    @Schema(description = "일정 상세 정보 DTO")
    public static class ScheduleDetailOutDto {

        @Schema(description = "일정 ID", example = "1")
        private Long id;

        @Schema(description = "제목", example = "평일 저녁 클라이밍")
        private String title;

        @Schema(description = "설명", example = "볼더링 모임")
        private String description;

        @Schema(description = "시작 시간", example = "2025-09-22T18:00:00")
        private Timestamp startTime;

        @Schema(description = "종료 시간", example = "2025-09-22T20:00:00")
        private Timestamp endTime;

        @Schema(description = "장소", example = "더클라임 홍대점")
        private String location;

        @Schema(description = "스케줄 타입", example = "crew", allowableValues = {"crew", "personal"})
        private String scheduleType;

        @Schema(description = "크루 이름", example = "YoyoCrew")
        private String crewName;

        @Schema(description = "암장 이름", example = "더클라임 홍대점")
        private String gymName;

        @Schema(description = "작성자 이름", example = "Yoyo")
        private String createdBy;

        public static ScheduleDetailOutDto fromEntity(ScheduleEntity schedule){
            return ScheduleDetailOutDto.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .description(schedule.getDescription())
                    .startTime(schedule.getStartTime())
                    .endTime(schedule.getEndTime())
                    .location(schedule.getLocation())
                    .scheduleType(schedule.getScheduleType().name())
                    .crewName(schedule.getCrew().getCrewName())
                    .gymName(schedule.getGym().getGymName())
                    .createdBy(schedule.getUser().getUserName())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "일정 생성 요청 DTO")
    public static class ScheduleCreateInDto{

        @Schema(description = "제목", example = "주말 클라이밍 깜짝 이벤트", maxLength = 100)
        @NotBlank
        private String title;

        @Schema(description = "설명", example = "서울 암장에서 클라이밍 모임", maxLength = 500)
        private String description;

        @Schema(description = "시작 시간", example = "2025-09-25T10:00:00")
        @NotNull
        private Timestamp startTime;

        @Schema(description = "종료 시간", example = "2025-09-25T13:00:00")
        @NotNull
        private Timestamp endTime;

        @Schema(description = "장소", example = "서울숲 볼더링 암장", maxLength = 100)
        private String location;

        @Schema(description = "스케줄 타입", example = "crew", allowableValues = {"crew", "personal"})
        @NotBlank
        private String scheduleType;

        @Schema(description = "크루 ID", example = "101")
        private Long crewId;

        @Schema(description = "암장 ID", example = "10")
        private Long gymId;

        @Schema(description = "작성자 ID", example = "202")
        @NotNull
        private Long userId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "일정 생성 응답 DTO")
    public static class ScheduleCreateOutDto {

        @Schema(description = "생성된 일정 ID", example = "1")
        private Long scheduleId;

        @Schema(description = "생성 완료 메시지", example = "일정이 성공적으로 생성되었습니다.")
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "일정 참석/불참 요청 DTO")
    public static class ScheduleAttendInDto {

        @Schema(description = "일정 ID", example = "1")
        @NotNull
        @Positive
        private Long scheduleId;

        @Schema(description = "사용자 ID", example = "202")
        @NotNull
        private Long userId;

        @Schema(description = "참석 여부", example = "true")
        @NotNull
        private Boolean attend; // true = 참석, false = 불참
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "일정 참석/불참 응답 DTO")
    public static class ScheduleAttendOutDto {

        @Schema(description = "처리 완료 메시지", example = "참석이 등록되었습니다.")
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "클라이밍 기록 작성 요청 DTO")
    public static class ClimbingRecordCreateInDto {

        @Schema(description = "일정 ID", example = "1")
        @NotNull
        @Positive
        private Long scheduleId;

        @Schema(description = "사용자 ID", example = "202")
        @NotNull
        private Long userId;

        @Schema(description = "기록 내용", example = "최고 난이도 5.12 성공!")
        @NotBlank
        private String record;

        @Schema(description = "기록 시간", example = "2025-09-25T12:30:00")
        private Timestamp recordTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "클라이밍 기록 작성 응답 DTO")
    public static class ClimbingRecordCreateOutDto {

        @Schema(description = "기록 작성 완료 메시지", example = "기록이 성공적으로 저장되었습니다.")
        private String message;
    }
}
