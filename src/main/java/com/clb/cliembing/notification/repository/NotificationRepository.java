package com.clb.cliembing.notification.repository;

import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.notification.entity.NotificationEntity;
import com.clb.cliembing.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    // 특정 유저가 생성한 알림 조회
    List<NotificationEntity> findByUser(UserEntity user);

    // 특정 유저가 수신한 알림 조회
    List<NotificationEntity> findByReciever(Long recieverId);

    // 특정 유저가 읽지 않은 알림 조회
    List<NotificationEntity> findByIsReadFalseAndReciever(Long recieverId);

    // 특정 크루 관련 알림 조회
    List<NotificationEntity> findByCrew(CrewEntity crew);

    // 알림 타입으로 필터링
    List<NotificationEntity> findByType(NotificationEntity.NotificationType type);

    // 소프트 삭제되지 않은 알림
    List<NotificationEntity> findByIsDeletedFalse();

    // 수신자 + 타입으로 조회
    List<NotificationEntity> findByRecieverAndType(Long recieverId, NotificationEntity.NotificationType type);
}
