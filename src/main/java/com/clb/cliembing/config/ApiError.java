package com.clb.cliembing.config;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "에러 정보")
public record ApiError(
        @Schema(description = "에러 코드", example = "VALIDATION_ERROR") String code,
        @Schema(description = "메시지", example = "요청 값이 올바르지 않습니다.") String message,
        @Schema(description = "상세", example = "field=name, error=must not be blank") String detail
) {}

