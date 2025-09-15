package com.clb.cliembing.crew.dto;

import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Comment("API 응답용 - 생성/조회 공통")
public class CrewResponseDto {

    private Long crewId;

    private String crewName;

    private String description;

    private String recruitmentStatus;

    private Boolean isPublic;

    private int memberCount;

    private String region;

    private String preferredAge;

    private Float activityScore;

    private Long mainPicId;

    private OwnerInfo owner;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OwnerInfo {
        private Long id;
        private String userId;
        private String userName;
    }
}
