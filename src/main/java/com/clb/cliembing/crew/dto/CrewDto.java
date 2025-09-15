package com.clb.cliembing.crew.dto;

import com.clb.cliembing.crew.entity.CrewEntity;
import lombok.*;

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

    private int memberCount;

    private String region;

    private String preferredAge;

    private Float activityScore;

    private Long mainPicId;

    private Long ownerId;       // UserEntity 참조를 ID로 단순화
    private String ownerUserId; // 필요하면 owner의 userId

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

}
