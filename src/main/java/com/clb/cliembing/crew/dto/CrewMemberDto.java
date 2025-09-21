package com.clb.cliembing.crew.dto;

import com.clb.cliembing.crew.entity.CrewMemberEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewMemberDto {

    private Long crewMemberId;

    private Long crewId;

    private Long userId;

    private String role;

    private String userLoginId; // user.getUserId()
    private String userName;    // user.getUserName()

    //Entity → DTO 변환
    public static CrewMemberDto fromEntity(CrewMemberEntity crewMember){
        return CrewMemberDto.builder()
                .crewMemberId(crewMember.getCrewMemberId())
                .crewId(crewMember.getCrew().getCrewId())
                .userId(crewMember.getUser().getId())
                .role(crewMember.getRole())
                .userLoginId(crewMember.getUser().getUserId())
                .userName(crewMember.getUser().getUserName())
                .build();
    }

    //크루 멤버 생성 요청 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Comment("크루 멤버 생성 요청용")
    public static class CrewMemberCreateInDto{
        private Long crewId;
        private Long userId;
        private String role;
    }

    //크루 멤버 조회 응답 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CrewMemberSearchOutDto{
        private Long crewMemberId;
        private String role;
        private UserInfo user;
    }

    //필요한 최소한의 사용자 정보만
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserInfo{
        private Long id;
        private String userId;
        private String userName;
    }

    /**
     * CrewMemberEntity를 CrewMemberOutDto로 변환합니다.
     * 주로 API 응답 시 사용 (user 정보를 하나의 객체로 묶어 표현 → 더 명확한 구조 제공)
     *
     * @param entity 변환할 CrewMemberEntity
     * @return CrewMemberOutDto 변환 결과
     */
    public static CrewMemberSearchOutDto toOutDto(CrewMemberEntity entity) {
        return CrewMemberSearchOutDto.builder()
                .crewMemberId(entity.getCrewMemberId())
                .role(entity.getRole())
                .user(UserInfo.builder()
                        .id(entity.getUser().getId())
                        .userId(entity.getUser().getUserId())
                        .userName(entity.getUser().getUserName())
                        .build())
                .build();
    }
}
