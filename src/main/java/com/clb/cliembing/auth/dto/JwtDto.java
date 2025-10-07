package com.clb.cliembing.auth.dto;

import lombok.*;

import java.util.List;

public class JwtDto {

    @Data
    @Builder
    public static class IssueTokenResponseDto {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }

    @Getter @Setter @NoArgsConstructor
    public static class MetaData  {
        private String userId;
        private String userName;
        private List<String> roles;
        private List<CrewRoleDto> crewRole;
        private List<GymRoleDto> gymRole;
    }

    @Getter @Setter @Builder
    public static class CrewRoleDto {
        private Long crewId;
        private String crewName;
        private String crewRole;
    }

    @Getter @Setter @Builder
    public static class GymRoleDto {
        private Long gymId;
        private String gymName;
        private String gymRole;
    }
}
