package com.clb.cliembing.board.repository;

import com.clb.cliembing.board.entity.BoardEntity;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 특정 크루의 게시글 전체 조회 (삭제되지 않은 것만)
    List<BoardEntity> findAllByCrewAndIsDeletedFalse(CrewEntity crew);

    // 특정 작성자의 게시글 조회
    List<BoardEntity> findAllByWriterAndIsDeletedFalse(UserEntity writer);

    // 공지사항만 조회 (크루별)
    List<BoardEntity> findAllByCrewAndIsNoticeTrueAndIsDeletedFalse(CrewEntity crew);

    // 크루 없이 작성된 공지사항 (전체 공지 등)
    List<BoardEntity> findAllByCrewIsNullAndIsNoticeTrueAndIsDeletedFalse();

    // 특정 키워드 포함된 제목 검색 (단순 Like 검색)
    List<BoardEntity> findByTitleContainingAndIsDeletedFalse(String keyword);
}
