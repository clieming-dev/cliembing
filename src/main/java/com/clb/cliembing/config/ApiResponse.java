package com.clb.cliembing.config;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "API 표준 응답")
public record ApiResponse<T>(
        @Schema(description = "HTTP Status code", example = "200") int status,
        @Schema(description = "성공 시 데이터") T data,
        @Schema(description = "에러 정보") ApiError error,
        @Schema(description = "요청 경로") String path,
        @Schema(description = "응답 시각(UTC)") Instant timestamp
) {
    public static <T> ApiResponse<T> ok(T data, int status, String path) {
        return new ApiResponse<>(status, data, null, path, Instant.now());
    }
    public static <T> ApiResponse<T> error(ApiError err, int status, String path) {
        return new ApiResponse<>(status, null, err, path, Instant.now());
    }
}


