package com.clb.cliembing.crew.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import com.clb.cliembing.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Slf4j
@DynamicInsert
@Comment("크루(팀) 기본 정보 테이블")
@SQLRestriction(value = "is_deleted = false")
@SQLDelete(sql = "UPDATE tbl_crew SET is_deleted = true WHERE id = ?")
@Data
@Entity
@Builder
@Table(name ="tbl_crew")
public class CrewEntity extends BaseEntity {
    @Comment("크루 관리 아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crewId;


    @Column(unique = true)
    private String crewName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId", referencedColumnName = "id")
    @Comment("크루 생성자")
    private UserEntity owner;

    private String description;

    private String recruitmentStatus;

    private Boolean isPublic;

    private int memberCount;

    private String region;

    private String preferredAge;

    private Float activityScore;

    private Long mainPicId;

}
