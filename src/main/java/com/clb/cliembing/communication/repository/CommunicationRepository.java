package com.clb.cliembing.communication.repository;

import com.clb.cliembing.communication.entity.CommunicationEntity;
import com.clb.cliembing.communication.entity.CommunicationEntity.CommunicationType;
import com.clb.cliembing.crew.entity.CrewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<CommunicationEntity, Long> {

    // 특정 크루의 공지사항 리스트
    List<CommunicationEntity> findByCrewAndTypeOrderByCreatedAtDesc(CrewEntity crew, CommunicationType type);

    // 특정 게시글의 댓글 목록
    List<CommunicationEntity> findByParent_CommunicationIdOrderByCreatedAtAsc(Long parentId);

    // 뉴스피드 용 전체 게시글 (공지/일반글)
    List<CommunicationEntity> findByCrewAndTypeInOrderByCreatedAtDesc(CrewEntity crew, List<CommunicationType> types);

    // 고정 공지사항만 가져오기
    List<CommunicationEntity> findByCrewAndTypeAndIsPinnedTrueOrderByCreatedAtDesc(CrewEntity crew, CommunicationType type);
}
