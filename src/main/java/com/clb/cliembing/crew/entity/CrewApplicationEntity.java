package com.clb.cliembing.crew.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import com.clb.cliembing.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLDelete(sql = "UPDATE tbl_crew_application SET is_deleted = true WHERE crewApplicationId = ?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="tbl_crew_application")
public class CrewApplicationEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crewApplicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="crew_id",nullable = false)
    @Comment("신청 대상 크루")
    private CrewEntity crew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @Comment("신청자 (사용자)")
    private UserEntity user;

    @Column(columnDefinition = "TEXT")
    @Comment("가입 신청 메세지")
    private String message;

    @Column(length = 20)
    @Comment("신청상태 - pending,approved,reject 등")
    private String status;

}
