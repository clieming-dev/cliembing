package com.clb.cliembing.schedule.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.gym.entity.GymEntity;
import com.clb.cliembing.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor
@AllArgsConstructor
@Comment("일정 정보 테이블")
@SQLRestriction(value = "is_deleted = false")
@SQLDelete(sql = "UPDATE tbl_schedule SET is_deleted = true WHERE scheduleId = ?")
@Data
@Entity
@Builder
@Table(name="tbl_schedule")
public class ScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gym_id", referencedColumnName = "id",nullable = false)
    @Comment("암장 ID")
    private GymEntity gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    @Comment("유저ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id", referencedColumnName = "id",nullable = false)
    private CrewEntity crew;

    @Comment("스케쥴 제목")
    private String title;

    @Comment("스케줄 설명")
    private String description;

    @Comment("시작 시간")
    private java.sql.Timestamp startTime;

    @Comment("종료 시간")
    private java.sql.Timestamp endTime;

    private String location;

    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false)
    @Comment("스케줄 타입 (crew/personal)")
    private ScheduleType scheduleType;

    public enum ScheduleType {
        crew, personal
    }
}
