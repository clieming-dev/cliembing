package com.clb.cliembing.board.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name="board")
@SQLDelete(sql="UPDATE board SET is_deleted = true WHERE board_id = ?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", referencedColumnName = "id", nullable = false)
    @Comment("작성자")
    private UserEntity writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="crew_id",referencedColumnName = "crewId")
    @Comment("관련 크루 (없을수도 있음)")
    private CrewEntity crew;

    @Column(length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean isNotice;

    private String picId;
}
