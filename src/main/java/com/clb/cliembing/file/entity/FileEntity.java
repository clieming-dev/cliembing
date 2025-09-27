package com.clb.cliembing.file.entity;


import com.clb.cliembing.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Slf4j
@DynamicInsert
@Comment("유저 테이블")
@Entity
@Table(name = "tbl_file_info")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE tbl_file_info SET is_deleted = true where id = ?")
@Getter
@Setter
@NoArgsConstructor
public class FileEntity extends BaseEntity {


    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id; // DB: BINARY(16)

    @Column(name = "file_name", nullable = false, length = 255) // 서버 저장 파일명(UUID.ext)
    private String fileName;

    @Column(name = "file_path", nullable = false, length = 512) // 절대 경로
    private String filePath;

    @Column(name = "file_type", nullable = false) // 상위 분류: 이미지는 'I'
    private Character fileType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "category", nullable = false)  // I:ICON, P:PROFILE, F:FEED, B:BOARD
    private Character category;

    @Column(name = "file_ext", length = 10)
    private String fileExt;

    @Column(name = "content_type", length = 100)
    private String contentType;
}
