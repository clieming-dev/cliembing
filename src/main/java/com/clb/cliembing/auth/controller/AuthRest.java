package com.clb.cliembing.auth.controller;


import com.clb.cliembing.auth.dto.JwtDto;
import com.clb.cliembing.auth.service.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRest {


    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String GRANT_TYPE = "grant_type";
    private static final String SCOPE = "scope";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final AuthService authService;

    @PostMapping("/token")
    public JwtDto.IssueTokenResponseDto issueToken(
            @RequestParam(name = GRANT_TYPE) String grantType,
            @RequestParam(name = SCOPE, required = false) String scope,
            @RequestParam(name = USERNAME, required = false) String username,
            @RequestParam(name = PASSWORD, required = false) String password,
            @RequestParam(name = REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletRequest httpServletRequest
    ) throws AuthException {
        log.info(httpServletRequest.getHeader("Authorization"));
        authService.parseAndValidateBasic(httpServletRequest.getHeader("Authorization"));
        switch (grantType) {
            case "client_credentials":
                return authService.issueToken(username);
            case "password":
                // TODO 비밀번호와 리프레시토큰은 추후 만들자..
                return authService.issueToken(username);
            case "refresh_token":
                return authService.issueToken(username);
            default:
                throw new AuthException("지원하지 않는 grant_type 입니다: " + grantType);
        }
    }
}
