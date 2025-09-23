package com.clb.cliembing.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice(basePackages = "com.clb.cliembing")
public class GlobalApiAdvice implements org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice<Object> {

    // 래핑 제외 경로
    private static boolean isSwaggerOrStatic(ServerHttpRequest request) {
        String p = request.getURI().getPath();
        return p.startsWith("/v3/api-docs")
                || p.startsWith("/swagger-ui")
                || p.startsWith("/swagger-resources")
                || p.startsWith("/webjars")
                || p.startsWith("/actuator")
                || p.endsWith("/openapi.json") || p.endsWith("/openapi.yaml");
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body, MethodParameter returnType, MediaType contentType,
            Class<? extends HttpMessageConverter<?>> converterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        // Swagger/OpenAPI/정적/리소스/이미 래핑된 경우 제외
        if (isSwaggerOrStatic(request)) return body; // swagger 리소스 훼손 방지 [web:147][web:106]
        if (body instanceof ApiResponse<?> || body instanceof org.springframework.core.io.Resource) return body; // 파일/스트림 제외 [web:106]

        // ResponseEntity는 상태/헤더 보존
        if (body instanceof ResponseEntity<?> entity) {
            int status = entity.getStatusCode().value();
            var wrapped = ApiResponse.ok(entity.getBody(), status, request.getURI().getPath());
            return ResponseEntity.status(entity.getStatusCode()).headers(entity.getHeaders()).body(wrapped);
        }

        Integer status = null;
        if (response instanceof ServletServerHttpResponse servletResp) {
            status = servletResp.getServletResponse().getStatus();
        }
        if (status == null) status = 200;

        return ApiResponse.ok(body, status, request.getURI().getPath());
    }

    // 예외 처리: 상태코드와 표준 에러 바디 구성
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(
            org.springframework.web.bind.MethodArgumentNotValidException ex, HttpServletRequest req) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + "=" + err.getDefaultMessage())
                .reduce((a, b) -> a + ", " + b).orElse("N/A");
        ApiError err = new ApiError("VALIDATION_ERROR", "요청 값이 올바르지 않습니다.", detail);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(err, HttpStatus.BAD_REQUEST.value(), req.getRequestURI()));
    }

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(org.springframework.web.server.ResponseStatusException ex, HttpServletRequest req) {
        ApiError err = new ApiError(Objects.toString(ex.getReason(), ex.getStatusCode().toString()), ex.getMessage(), null);
        return ResponseEntity.status(ex.getStatusCode())
                .body(ApiResponse.error(err, ex.getStatusCode().value(), req.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError("INTERNAL_ERROR", "서버 오류가 발생했습니다.", ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(err, HttpStatus.INTERNAL_SERVER_ERROR.value(), req.getRequestURI()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoElement(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError("NO_SUCH_ELEMENT", "sss.", ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(err, HttpStatus.INTERNAL_SERVER_ERROR.value(), req.getRequestURI()));
    }
}
