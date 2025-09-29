package com.clb.cliembing.communication.service;

import com.clb.cliembing.communication.dto.CommuManageDto;
import com.clb.cliembing.communication.entity.CommunicationEntity;
import com.clb.cliembing.communication.entity.CommunicationEntity.CommunicationType;
import com.clb.cliembing.communication.repository.CommunicationRepository;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.crew.repository.CrewRepository;
import com.clb.cliembing.user.entity.UserEntity;
import com.clb.cliembing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommuManageService {

    private final CommunicationRepository communicationRepository;
    private final CrewRepository crewRepository;
    private final UserRepository userRepository;

    /**
     * 공지사항 / 게시글 생성
     */
    public CommuManageDto.PostCreateOutDto createPost(CommuManageDto.PostCreateInDto inDto) {
        CrewEntity crew = crewRepository.findById(inDto.getCrewId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 크루입니다."));
        UserEntity writer = userRepository.findById(inDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        CommunicationType type = switch (inDto.getType().toLowerCase()) {
            case "notice" -> CommunicationType.NOTICE;
            case "post" -> CommunicationType.POST;
            default -> throw new IllegalArgumentException("유효하지 않은 게시글 타입입니다.");
        };

        CommunicationEntity entity = CommunicationEntity.builder()
                .crew(crew)
                .writer(writer)
                .type(type)
                .title(inDto.getTitle())
                .content(inDto.getContent())
                .isPinned(inDto.isPinned())
                .build();

        communicationRepository.save(entity);

        return CommuManageDto.PostCreateOutDto.builder()
                .communicationId(entity.getCommunicationId())
                .message(type.name() + "가 성공적으로 작성되었습니다.")
                .build();
    }

    /**
     * 공지사항 / 게시글 조회
     */
    public List<CommuManageDto.PostSearchOutDto> getPosts(CommuManageDto.PostSearchInDto inDto) {
        CrewEntity crew = crewRepository.findById(inDto.getCrewId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 크루입니다."));

        CommunicationType type = switch (inDto.getType().toLowerCase()) {
            case "notice" -> CommunicationType.NOTICE;
            case "post" -> CommunicationType.POST;
            default -> throw new IllegalArgumentException("유효하지 않은 게시글 타입입니다.");
        };

        List<CommunicationEntity> entities = communicationRepository.findByCrewAndTypeOrderByCreatedAtDesc(crew, type);

        return entities.stream()
                .map(this::toPostSearchOutDto)
                .collect(Collectors.toList());
    }

    /**
     * 뉴스피드 조회 (공지 + 게시글)
     */
    public List<CommuManageDto.NewsfeedOutDto> getNewsfeed(Long crewId) {
        CrewEntity crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 크루입니다."));

        List<CommunicationEntity> entities = communicationRepository.findByCrewAndTypeInOrderByCreatedAtDesc(crew,
                List.of(CommunicationType.NOTICE, CommunicationType.POST));

        // TODO: 댓글 수, 좋아요 수 계산 로직 필요

        return entities.stream()
                .map(this::toNewsfeedOutDto)
                .collect(Collectors.toList());
    }

    /**
     * 댓글 작성
     */
    public CommuManageDto.CommentCreateOutDto createComment(CommuManageDto.CommentCreateInDto inDto) {
        UserEntity writer = userRepository.findById(inDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        CommunicationEntity parent = communicationRepository.findById(inDto.getParentId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 부모 게시글입니다."));

        CommunicationEntity comment = CommunicationEntity.builder()
                .crew(parent.getCrew())
                .writer(writer)
                .type(CommunicationType.COMMENT)
                .content(inDto.getContent())
                .parent(parent)
                .build();

        communicationRepository.save(comment);

        return CommuManageDto.CommentCreateOutDto.builder()
                .commentId(comment.getCommunicationId())
                .message("댓글이 등록되었습니다.")
                .build();
    }

    // DTO 변환 헬퍼 - 공지/게시글 조회 DTO
    private CommuManageDto.PostSearchOutDto toPostSearchOutDto(CommunicationEntity entity) {
        return CommuManageDto.PostSearchOutDto.builder()
                .communicationId(entity.getCommunicationId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .type(entity.getType().name().toLowerCase())
                .isPinned(entity.getIsPinned() != null && entity.getIsPinned())
                .build();
    }

    // DTO 변환 헬퍼 - 뉴스피드 DTO
    private CommuManageDto.NewsfeedOutDto toNewsfeedOutDto(CommunicationEntity entity) {
        return CommuManageDto.NewsfeedOutDto.builder()
                .communicationId(entity.getCommunicationId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .type(entity.getType().name().toLowerCase())
                .commentCount(0)  // TODO: 댓글 수 계산 로직 추가 필요
                .likeCount(0)     // TODO: 좋아요 수 계산 로직 추가 필요
                .build();
    }
}
