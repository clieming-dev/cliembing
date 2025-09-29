package com.clb.cliembing.gym.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "tbl_gym")
@SQLDelete(sql = "UPDATE tbl_gym SET is_deleted =true WHERE gym_id = ?")
@SQLRestriction("is_deleted =false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Comment("암장")
public class GymEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gymName;

    private String region;

    @Column(name = "open_time")
    private java.time.LocalTime openTime;

    @Column(name="close_time")
    private String closeTime;

    private String price;

    private Boolean hasParking;

    private String instagramUrl;

    @Comment("암장 혼잡도")
    private String congestionLevel;

    @Column(columnDefinition = "TEXT")
    private String newSetSchedule;

    private String mapUrl;

    private String mainPicId;
}
