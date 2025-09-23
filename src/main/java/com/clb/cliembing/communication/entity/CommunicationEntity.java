package com.clb.cliembing.communication.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "tbl_communication")
@SQLDelete(sql = "UPDATE tbl_communication SET is_deleted = true WHERE communication_id = ?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Comment("공지사항/게시글/댓글 통합 테이블")
public class CommunicationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id", nullable = false)
    @Comment("소속 크루")
    private CrewEntity crew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    @Comment("작성자")
    private UserEntity writer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("타입: NOTICE, POST, COMMENT")
    private CommunicationType type;

    @Column(length = 100)
    @Comment("제목 (공지, 게시글만 해당)")
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Comment("내용")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Comment("댓글의 경우 부모 CommunicationEntity 참조")
    private CommunicationEntity parent;

    @Builder.Default
    @Column(name = "is_pinned")
    @Comment("공지 상단 고정 여부")
    private Boolean isPinned = false;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public enum CommunicationType {
        NOTICE,
        POST,
        COMMENT
    }
}
