package com.clb.cliembing.common.utils;

import com.clb.cliembing.auth.dto.JwtDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static final String BEARER = "Bearer ";
    private final JwtDecoder jwtDecoder;
    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * HttpServletRequest에서 Bearer 토큰을 추출하고 MetaData를 파싱해 반환
     */
    public JwtDto.MetaData extractMetaData(HttpServletRequest request) throws AuthException {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith(BEARER)) {
            throw new AuthException("Authorization 헤더에 Bearer 토큰이 없습니다.");
        }
        String token = auth.substring(BEARER.length());

        Jwt jwt = decodeJwt(token);

        Object metadataClaim = jwt.getClaims().get("metadata");
        if (metadataClaim instanceof Map<?, ?> map) {
            return mapToMetaData((Map<?, ?>) metadataClaim);
        }
        try {
            String payloadJson = decodePayloadJson(token);
            Map<String, Object> payload = objectMapper.readValue(payloadJson, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
            Object meta = payload.get("metadata");
            if (meta instanceof Map<?, ?> m) {
                return mapToMetaData(m);
            }
        } catch (Exception e) {
            log.debug("payload 직접 파싱 실패: {}", e.getMessage());
        }
        JwtDto.MetaData assembled = new JwtDto.MetaData();
        assembled.setUserId((String) jwt.getClaims().get("userId"));
        assembled.setUserName((String) jwt.getClaims().get("userName"));
        assembled.setRoles(castList(jwt.getClaims().get("roles")));
        assembled.setCrewRole(objectMapper.convertValue(jwt.getClaims().get("crewRole"),
                objectMapper.getTypeFactory().constructCollectionType(java.util.List.class, JwtDto.CrewRoleDto.class)));
        assembled.setGymRole(objectMapper.convertValue(jwt.getClaims().get("gymRole"),
                objectMapper.getTypeFactory().constructCollectionType(java.util.List.class, JwtDto.GymRoleDto.class)));


        return assembled;
    }

    private Jwt decodeJwt(String token) throws AuthException {
        try {
            return jwtDecoder.decode(token);
        } catch (BadJwtException ex) {
            throw new AuthException("유효하지 않은 JWT: " + ex.getMessage());
        }
    }

    private String decodePayloadJson(String token) throws AuthException {
        String[] parts = token.split("\\.");
        if (parts.length < 2) throw new AuthException("JWT 형식이 올바르지 않습니다.");
        byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
        return new String(decoded, StandardCharsets.UTF_8);
    }


//    @SuppressWarnings("unchecked")
    private java.util.List<String> castList(Object value) {
        if (value == null) return null;
        if (value instanceof java.util.List<?> list) {
            java.util.List<String> out = new java.util.ArrayList<>(list.size());
            for (Object o : list) out.add(o == null ? null : String.valueOf(o));
            return out;
        }
        return null;
    }

    private JwtDto.MetaData mapToMetaData(Map<?, ?> map) {
        return objectMapper.convertValue(map, JwtDto.MetaData.class);
    }

}
