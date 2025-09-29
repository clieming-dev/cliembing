package com.clb.cliembing.auth.controller;


import com.clb.cliembing.auth.dto.JwtDto;
import com.clb.cliembing.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRest {

    private final AuthService authService;
    @PostMapping("/token")
    public JwtDto.IssueTokenResponseDto issueToken(@RequestParam(defaultValue = "guest") String userId) {

        return authService.issueToken(userId);
    }
}
