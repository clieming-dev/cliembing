package com.clb.cliembing.auth.service;


import com.clb.cliembing.auth.dto.JwtDto;
import com.clb.cliembing.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtEncoder encoder;
    private final JwtConfig.JwtProps props;

    public JwtDto.IssueTokenResponseDto issueToken(String userId) {
        Instant now = Instant.now();
        long ttl = props.ttlSeconds();

        // 어플리케이션의 사용자 롤
        List<String> roles = List.of("admin", "gym_admin");

        //크루별 사용자 롤
        List<JwtDto.CrewRoleDto> crewRole = List.of(
                JwtDto.CrewRoleDto.builder()
                        .crewId(1L)
                        .crewName("크루1")
                        .crewRole("owner")
                        .build(),
                JwtDto.CrewRoleDto.builder()
                        .crewId(2L)
                        .crewName("크루2")
                        .crewRole("user")
                        .build()
        );

        //암장별 사용자 롤
        List<JwtDto.GymRoleDto> gymRole = List.of(
                JwtDto.GymRoleDto.builder()
                        .gymId(100L)
                        .gymName("A암장")
                        .gymRole("manager")
                        .build(),
                JwtDto.GymRoleDto.builder()
                        .gymId(200L)
                        .gymName("B암장")
                        .gymRole("staff")
                        .build()
        );

        JwtDto.MetaData metaData = new JwtDto.MetaData();
        //todo user테이블 이후 만들기
        metaData.setUserName("양지웅");
        metaData.setRoles(roles);
        metaData.setCrewRole(crewRole);
        metaData.setGymRole(gymRole);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userId)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ttl))
                .claim("metaData", metaData)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return JwtDto.IssueTokenResponseDto.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(ttl)
                .build();
    }

}
