package com.clb.cliembing.file;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {


    @Data
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor
    public static class AllInfo {

        private String id;

        private String fileName;

        private String filePath;

        private Character fileType;

        private Long fileSize;
    }
}
