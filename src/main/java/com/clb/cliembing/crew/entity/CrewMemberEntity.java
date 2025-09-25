package com.clb.cliembing.crew.entity;

import com.clb.cliembing.common.entity.BaseEntity;
import com.clb.cliembing.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLDelete(sql = "UPDATE tbl_crewMember SET is_deleted = true WHERE crewMemberId = ?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("User ↔ Crew 다대다 관계를 1:N + N:1로 풀기 위함")
@Table(name = "tbl_crew_member")
public class CrewMemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id", referencedColumnName = "crewId", nullable = false)
    @Comment("소속된 크루")
    private CrewEntity crew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Comment("사용자")
    private UserEntity user;

    @Column(length = 50)
    @Comment("크루 내 역할")
    private String role;

}
