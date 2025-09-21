package com.clb.cliembing.crew.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
}
