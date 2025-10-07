package com.clb.cliembing.crew.dto;

import com.clb.cliembing.crew.entity.CrewMemberEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "크루 멤버 DTO")
public class CrewMemberDto {

    @Schema(description = "크루 멤버 ID", example = "123")
    private Long crewMemberId;

    @Schema(description = "크루 ID", example = "45")
    private Long crewId;

    @Schema(description = "유저 ID", example = "202")
    private Long userId;

    @Schema(description = "멤버 역할", example = "ADMIN")
    private String role;

    @Schema(description = "유저 로그인 아이디", example = "yoyo326")
    private String userLoginId; // user.getUserId()

    @Schema(description = "유저 이름", example = "Yoyo")
    private String userName;    // user.getUserName()

    //Entity → DTO 변환
    public static CrewMemberDto fromEntity(CrewMemberEntity crewMember){
        return CrewMemberDto.builder()
                .crewMemberId(crewMember.getId())
                .crewId(crewMember.getCrew().getId())
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
    @Schema(description = "크루 멤버 생성 요청 DTO")
    public static class CrewMemberCreateInDto{
        @Schema(description = "크루 ID", example = "45", required = true)
        private Long crewId;

        @Schema(description = "유저 ID", example = "202", required = true)
        private Long userId;

        @Schema(description = "멤버 역할", example = "MEMBER", required = true)
        private String role;
    }

    //크루 멤버 조회 응답 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "크루 멤버 조회 응답 DTO")
    public static class CrewMemberSearchOutDto{
        @Schema(description = "크루 멤버 ID", example = "202")
        private Long crewMemberId;

        @Schema(description = "멤버 역할", example = "ADMIN")
        private String role;

        @Schema(description = "유저 정보")
        private UserInfo user;
    }

    //필요한 최소한의 사용자 정보만
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "유저 정보 DTO")
    public static class UserInfo{
        @Schema(description = "유저 내부 ID", example = "789")
        private Long id;

        @Schema(description = "유저 로그인 아이디", example = "yoyo326")
        private String userId;

        @Schema(description = "유저 이름", example = "Yoyo")
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
                .crewMemberId(entity.getId())
                .role(entity.getRole())
                .user(UserInfo.builder()
                        .id(entity.getUser().getId())
                        .userId(entity.getUser().getUserId())
                        .userName(entity.getUser().getUserName())
                        .build())
                .build();
    }
}
