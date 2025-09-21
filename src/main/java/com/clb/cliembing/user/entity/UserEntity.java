package com.clb.cliembing.user.entity;


import com.clb.cliembing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Slf4j
@DynamicInsert
@Entity
@Table(name = "tbl_user")
@Comment("유저 테이블")
@Getter @Setter
@SQLRestriction(value = "is_deleted = false")
@SQLDelete(sql = "UPDATE tbl_user SET is_deleted = true where id = ?")
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Comment("사용자 관리 아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String userName;

    private String email;

    @Comment("사용자 로그인 패스워드")
    @Column
    private String password;

    private String profileImagePath;

    private String mbti;


    private Character gender;

    private String birth;

    private String mainPicId;
}
