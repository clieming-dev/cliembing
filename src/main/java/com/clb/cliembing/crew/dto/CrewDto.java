package com.clb.cliembing.crew.dto;

import com.clb.cliembing.crew.entity.CrewEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewDto {

    private Long crewId;

    private String crewName;

    private String description;

    private String recruitmentStatus;

    private Boolean isPublic;

    private Integer memberCount;

    private String region;

    private String preferredAge;

    private Float activityScore;

    private Long mainPicId;

    private Long ownerId;       // UserEntity 참조를 ID로 단순화
    private String ownerUserId; // 필요하면 owner의 userId

    //Entity → DTO 변환
    public static CrewDto fromEntity(CrewEntity crew) {
        return CrewDto.builder()
                .crewId(crew.getCrewId())
                .crewName(crew.getCrewName())
                .description(crew.getDescription())
                .recruitmentStatus(crew.getRecruitmentStatus())
                .isPublic(crew.getIsPublic())
                .memberCount(crew.getMemberCount())
                .region(crew.getRegion())
                .preferredAge(crew.getPreferredAge())
                .activityScore(crew.getActivityScore())
                .mainPicId(crew.getMainPicId())
                .ownerId(crew.getOwner().getId())
                .ownerUserId(crew.getOwner().getUserId())
                .build();
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Comment("크루 생성 요청용")
    public static class CrewCreateInDto {
        private String crewName;

        private String description;

        private String recruitmentStatus;

        private Boolean isPublic;

        private String region;

        private String preferredAge;

        private Long mainPicId;

        private Long ownerId;  // 어떤 유저가 만든 건지
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Comment("API 응답용 - 생성/조회 공통")
    public static class CrewSearchOutDto {

        private Long crewId;

        private String crewName;

        private String description;

        private String recruitmentStatus;

        private Boolean isPublic;

        private Integer memberCount;

        private String region;

        private String preferredAge;

        private Float activityScore;

        private Long mainPicId;

        private OwnerInfo owner;


    }

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
