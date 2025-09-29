package com.clb.cliembing.file.controller;


import com.clb.cliembing.file.dto.FileDto;
import com.clb.cliembing.file.service.FileService;
import com.clb.cliembing.file.entity.ImageCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File", description = "이미지 업로드 API")
public class FileRest {

    private final FileService fileService;

    @Operation(summary = "이미지 업로드", description = "이미지 파일만 업로드를 허용하며 카테고리 정책에 따라 압축/거부가 적용됩니다.")
    @PostMapping(value = "/upload/{category}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileDto> uploadImage(
            @Parameter(description = "이미지 카테고리(ICON, PROFILE, FEED, BOARD)", example = "PROFILE", required = true)
            @PathVariable("category") ImageCategory category,
            @Parameter(description = "업로드할 이미지 파일", required = true)
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        FileDto info = fileService.saveImage(file, category);
        return ResponseEntity.ok(info);
    }
}
