package com.clb.cliembing.file.dto;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileDto {

    private UUID id;              // 저장 식별자
    private String fileName;        // 서버 저장 파일명(UUID.ext)
    private String originalName;    // 원본 파일명
    private String filePath;        // 내부 경로(필요 시 마스킹 권장)
    private Character fileType;     // 상위 분류('I')
    private Character category;     // 'I','P','F','B'
    private Long fileSize;          // 저장된 바이트 수
    private String contentType;     // MIME
    private String fileExt;         // 확장자

    @Data
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor
    public static class SearchInInfo {
        private UUID id;              // 저장 식별자
    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor
    public static class infoDto {
        private UUID id;              // 저장 식별자
        private String originalName;    // 원본 파일명
        private Long fileSize;          // 저장된 바이트 수
        private String contentType;     // MIME
        private String fileExt;         // 확장자

    }

}
