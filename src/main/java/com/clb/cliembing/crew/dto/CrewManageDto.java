package com.clb.cliembing.crew.dto;


import com.clb.cliembing.crew.entity.CrewEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
public class CrewManageDto {



    @Getter
    public static class CrewSearchInDto {

        @Schema(description = "검색 키워드 (크루명, 설명, 지역 등)",
                example = "클아이밍",
                maxLength = 10)
        private String keyword;

        @Schema(description = "지역 필터",
                example = "서울",
                maxLength = 50)
        private String region;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "크루 검색 결과 아이템 DTO")
    public static class CrewSearchOutDto {

        @Schema(description = "크루 ID", example = "101")
        private Long crewId;

        @Schema(description = "크루명", example = "서울 클라이밍 크루", maxLength = 100)
        private String crewName;

        @Schema(description = "크루 소개/설명",
                example = "서울 지역 암벽/볼더링을 즐기는 모임입니다.", maxLength = 500)
        private String description;

        @Schema(description = "현재 멤버 수", example = "24", minimum = "0")
        private Integer memberCount;

        @Schema(description = "활동 지역", example = "서울", maxLength = 50)
        private String region;

        @Schema(description = "선호 연령대",
                example = "20대",
                allowableValues = {"10대","20대","30대","40대","50대이상"})
        private String preferredAge;

        @Schema(description = "활동 점수 (0.0~5.0)",
                example = "4.3", minimum = "0.0", maximum = "5.0")
        private Float activityScore;

        @Schema(description = "메인 사진 id", example = "8f2a1bkklf")
        private String mainPicId;
    }

    @Getter
    public static class CrewDetailInDto {

        @Schema(description = "크류 ID",
                example = "1")
        @NotNull @Positive
        private Long crewId;
    }

    @Data
    @Builder
    public static class CrewDetailOutDto {


        @Schema(description = "크루 ID", example = "1")
        Long crewId;

        @Schema(description = "크루명", example = "서울 클라이밍")
        String crewName;

        @Schema(description = "설명", example = "서울 클라이밍크루")
        String description;

        @Schema(description = "모집 상태", example = "RECRUITING", allowableValues = {"RECRUITING","CLOSED","PAUSED"})
        String recruitmentStatus;

        @Schema(description = "공개 여부", example = "true")
        Boolean isPublic;

        @Schema(description = "멤버 수", example = "49")
        Integer memberCount;

        @Schema(description = "지역", example = "영등포구")
        String region;

        @Schema(description = "선호 연령대", example = "30대", allowableValues = {"10대","20대","30대","40대","50대이상"})
        String preferredAge;

        @Schema(description = "활동 점수(0.0~5.0)", example = "4.7")
        Float activityScore;

        @Schema(description = "메인 사진 ID", example = "1001")
        Long mainPicId;

        @Schema(description = "크루장 이름", example = "홍길동")
        String ownerName;

        public static CrewDetailOutDto fromEntity(CrewEntity crew){
            return CrewDetailOutDto.builder()
                    .crewId(crew.getId())
                    .crewName(crew.getCrewName())
                    .description(crew.getDescription())
                    .recruitmentStatus(crew.getRecruitmentStatus())
                    .isPublic(crew.getIsPublic())
                    .memberCount(crew.getMemberCount())
                    .region(crew.getRegion())
                    .preferredAge(crew.getPreferredAge())
                    .activityScore(crew.getActivityScore())
                    .mainPicId(crew.getMainPicId())
                    .ownerName(crew.getOwner().getUserName())
                    .build();

        }
    }

    @Getter
    public static class JoinCrewInDto {

        @Schema(description = "가입인사",
                example = "안녕하세요 주로 구로에서 클라이밍하고 있는 양지웅입니다..")
        private String content;

        @Schema(description = "크루 Id")
        private Long crewId;
    }

}
