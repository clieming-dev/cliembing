package com.clb.cliembing.file.service;

import com.clb.cliembing.config.UuidV7Generator;
import com.clb.cliembing.file.dto.FileDto;
import com.clb.cliembing.file.entity.FileEntity;
import com.clb.cliembing.file.entity.ImageCategory;
import com.clb.cliembing.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("${app.file.upload-root}")
    private String uploadRoot;

    private final FileRepository fileRepository;

    private final UuidV7Generator uuidV7Generator;

    @Transactional
    public FileDto saveImage(MultipartFile file, ImageCategory category) throws IOException {

        // 1. input 확인
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
        final String original = Objects.requireNonNullElse(file.getOriginalFilename(), "image");
        final String ext = extractExt(original);
        if (!ImageCategory.isAllowedExtension(ext)) throw new IllegalArgumentException("이미지 확장자만 허용됩니다(jpg, jpeg, png, webp).");
        final String mime = file.getContentType();
        if (!ImageCategory.isAllowedMime(mime)) throw new IllegalArgumentException("이미지 MIME 타입만 허용됩니다.");
        // todo 10메가도 크지않나.. 서버용량확인해보기
        if (file.getSize() > 10L * 1024 * 1024) throw new IllegalArgumentException("최대 10MB까지 업로드 가능합니다.");

        // 2. 폴더경로 생상
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        Path dir = Paths.get(uploadRoot,
                String.valueOf(today.getYear()),
                String.format("%02d", today.getMonthValue()),
                String.format("%02d", today.getDayOfMonth()),
                String.valueOf(category.getCode()).toLowerCase(Locale.ROOT)); // i/p/f/b 소문자 폴더ㅓ
        Files.createDirectories(dir); // 멱등 [web:233]

        // 3.파일명
        UUID id = uuidV7Generator.generate(); // v7 [web:292]
        String storedName = ext.isBlank() ? id.toString() : id + "." + ext.toLowerCase(Locale.ROOT);
        Path savePath = dir.resolve(storedName);

        // 4.파일저장
        long finalSize;
        if (category.isCompressIfOver() && file.getSize() > category.getMaxBytes()) {
            byte[] compressed = compressImage(file.getBytes(), ext, category.getCompression());
            try (OutputStream out = Files.newOutputStream(savePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                out.write(compressed);
            }
            finalSize = compressed.length;
        } else if (!category.isCompressIfOver() && file.getSize() > category.getMaxBytes()) {
            throw new IllegalArgumentException(category.name() + "는 " + (category.getMaxBytes() / (1024 * 1024)) + "MB 이하만 허용됩니다.");
        } else {
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
            }
            finalSize = Files.size(savePath);
        }

        // 5. DB저장
        FileEntity entity = FileEntity.create(id, storedName, savePath.toString(),
                'I', finalSize, category.getCode(), ext, mime);
        fileRepository.save(entity);

        // todo savePath 응답시에 꼭 빼기!!!! id랑 filename만 보내면 되지않을까..? filename도 뺄까..
        return FileDto.builder()
                .id(id)
                .fileName(storedName)
                .originalName(original)
                .filePath(savePath.toString())
                .fileType('I')
                .category(category.getCode())
                .fileSize(finalSize)
                .contentType(mime)
                .fileExt(ext)
                .build();
    }

    private String extractExt(String original) {
        if (original == null) return "";
        int dot = original.lastIndexOf('.');
        return (dot > -1 && dot < original.length() - 1) ? original.substring(dot + 1) : "";
    }

    //todo 나중에 구현
    private byte[] compressImage(byte[] input, String ext, ImageCategory.CompressionLevel level) {
        return input;
    }
}
