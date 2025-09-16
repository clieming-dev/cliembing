package com.clb.cliembing.auth.controller;


import com.clb.cliembing.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRest {


    private final JwtEncoder encoder;
    private final JwtConfig.JwtProps props;

    @PostMapping("/token")
    public Map<String, String> issueToken(@RequestParam(defaultValue = "guest") String sub) {
        Instant now = Instant.now();
        long ttl = props.ttlSeconds();
        log.info(">>> JwtEncoder 구현체: {}", encoder.getClass().getName());  // 확인용 로그

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(sub)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ttl))
                .claim("scope", "api.read api.write")
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
//        String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return Map.of("access_token", token, "token_type", "Bearer", "expires_in", String.valueOf(ttl));
    }
}
