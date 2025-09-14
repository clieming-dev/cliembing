package com.clb.cliembing.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 생성자/등록자 이이디
     */
    @CreatedBy
//	@NonNull
    //@Column(name = "created_by_user_id", length = 256, nullable = false, updatable = false) // columnDefinition = "varchar(256) // comment '등록자아이디'" <== MySQL
    @Column(name = "created_by", length = 256, updatable = false) // columnDefinition = "varchar(256) // comment '등록자아이디'" <== MySQL
    @Comment("등록자아이디")
    // private String createdByUserId;
    private String createdBy;

    /**
     * 생성일자/등록일자
     */
    @CreatedDate
    @ColumnDefault("now()")
//	@NonNull
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Comment("등록일시")
    private OffsetDateTime createdAt;
    //private Date createdAt;


    /**
     * 수정자 아이디
     */
    @LastModifiedBy
    @Column(name = "updated_by", length = 256, insertable = false)
    @Comment("수정자아이디")
    private String updatedBy;

    /**
     * 수정일자
     */
    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)//, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Comment("수정일시")
    private OffsetDateTime updatedAt;
    //private Date updatedAt;

    @Comment("삭제 여부")
    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted;

}
