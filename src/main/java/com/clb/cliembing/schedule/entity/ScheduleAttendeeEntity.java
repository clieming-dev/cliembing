package com.clb.cliembing.schedule.entity;

import com.clb.cliembing.common.entity.BaseEntity;
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
@SQLDelete(sql = "UPDATE tbl_schedule_attendee SET is_deleted = true WHERE schedule_attendee_id = ?")
@SQLRestriction("is_deleted = false")
@Builder
@Data
@Entity
@Table(name = "tbl_schedule_attendee")
@Comment("스케쥴 참석자 정보 테이블")
public class ScheduleAttendeeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ScheduleEntity schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    @Comment("암장 ID")
    private GymEntity gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("유저 ID")
    private UserEntity user;

    @Column(name = "is_attending", nullable = false)
    @Comment("참석 여부")
    private Boolean isAttending;

}
