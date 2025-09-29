package com.clb.cliembing.notification.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Builder
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE tbl_notification SET is_deleted = true WHERE notification_id = ?")
@Data
@Entity
@Comment("알림 테이블")
@Table(name="tbl_notification")
public class NotificationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @Comment("알림 생성 유저")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="crew_id", nullable = false)
    @Comment("관련 크루")
    private CrewEntity crew;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Comment("알림 타입 (crew_notice, crew_post, crew_schedule, personal_schedule, crew_application 등)")
    private NotificationType type;

    @Comment("알림 제목")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Boolean isRead = false;

    @Comment("알림 수신 유저 ID (FK 아님 - 참조용)")
    private Long reciever;

    @Comment("추가 필드 정보")
    private String field;

    public enum NotificationType {
        crew_notice,
        crew_post,
        crew_schedule,
        personal_schedule,
        crew_application
    }
}
