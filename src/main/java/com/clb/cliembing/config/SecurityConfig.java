package com.clb.cliembing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private static final String[] SWAGGER = {
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html"
    };
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    ApiAuthenticationEntryPoint entryPoint,
                                    ApiAccessDeniedHandler deniedHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER).permitAll()
                        .requestMatchers("/auth/token").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPoint)      // 401 커스텀 [web:178]
                        .accessDeniedHandler(deniedHandler)        // 403 커스텀 [web:183]
                );
        return http.build();
    }


}
