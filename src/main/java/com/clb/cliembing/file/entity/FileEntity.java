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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@DynamicInsert
@Entity
@Table(name = "tbl_file_info")
@Comment("유저 테이블")
@Getter
@Setter
@SQLRestriction(value = "is_deleted = false")
@SQLDelete(sql = "UPDATE tbl_file_info SET is_deleted = true where id = ?")
@NoArgsConstructor
public class FileEntity extends BaseEntity {

    @Comment("파일 관리 아이디")
    @Id
    private String id;


    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private Character fileType;

    @Column(name = "file_size")
    private Long fileSize;

}
