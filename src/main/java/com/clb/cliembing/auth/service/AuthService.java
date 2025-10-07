package com.clb.cliembing.auth.service;


import com.clb.cliembing.auth.dto.JwtDto;
import com.clb.cliembing.config.JwtConfig;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtEncoder encoder;
    private final JwtConfig.JwtProps props;


    @Value("${auth.client.id}") private String fixedId;
    @Value("${auth.client.secret}") private String fixedSecret;



    public JwtDto.IssueTokenResponseDto issueToken(String userId) {
        Instant now = Instant.now();
        long ttl = props.ttlSeconds();


        //grant type에따라 분기처리하고싶음 password,clientcredential,refresh만 현재 생각하고있음


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
        metaData.setUserId(userId);
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


    public void parseAndValidateBasic(String headerAuthorization) throws AuthException {
        if (headerAuthorization == null || !headerAuthorization.regionMatches(true, 0, "Basic ", 0, 6)) {
            throw new AuthException("Basic 토큰이 존재하지 않습니다.");
        }
        try {
            String enc = headerAuthorization.split(" ", 2)[1];
            String decoded = new String(Base64.getDecoder().decode(enc));
            String[] parts = decoded.split(":", 2);
            if (parts.length != 2) throw new IllegalArgumentException("basic 인증 포맷 오류");
            String clientId = parts[0];
            String clientSecret = parts[1];
            if (!this.isValid(clientId, clientSecret)) {
                throw new AuthException("client 정보가 올바르지 않습니다. basic 인증을 확인해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthException(e.getMessage());
        }
    }

    private boolean isValid(String clientId, String clientSecret) {
        return Objects.equals(fixedId, clientId) && Objects.equals(fixedSecret, clientSecret);
    }

}
