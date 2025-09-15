package com.clb.cliembing.crew.dto;

import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Comment("크루 생성 요청용")
public class CrewCreateRequestDto {
    private String crewName;

    private String description;

    private String recruitmentStatus;

    private Boolean isPublic;

    private String region;

    private String preferredAge;

    private Long mainPicId;

    private Long ownerId;  // 어떤 유저가 만든 건지
}
