package com.clb.cliembing.crew.service;

import com.clb.cliembing.crew.dto.CrewDto;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.crew.repository.CrewRepository;
import com.clb.cliembing.user.entity.UserEntity;
import com.clb.cliembing.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository crewRepository;
    private final UserRepository userRepository;

    /**
     * 크루 생성
     */
    @Transactional
    public Long createCrew(CrewDto.CrewCreateInDto dto) {
        // ownerId로 UserEntity 조회
        UserEntity owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        CrewEntity crew = CrewEntity.builder()
                .crewName(dto.getCrewName())
                .description(dto.getDescription())
                .recruitmentStatus(dto.getRecruitmentStatus())
                .isPublic(dto.getIsPublic())
                .region(dto.getRegion())
                .preferredAge(dto.getPreferredAge())
                .mainPicId(dto.getMainPicId())
                .memberCount(1) // 기본값
                .activityScore(0f) // 기본값
                .owner(owner)
                .build();

        crewRepository.save(crew);
        return crew.getId();
    }

    /**
     * 단일 조회
     */
    public CrewDto getCrew(Long crewId) {
        CrewEntity crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 크루가 존재하지 않습니다."));
        return CrewDto.fromEntity(crew);
    }

    /**
     * 전체 조회
     */
    public List<CrewDto> getAllCrews() {
        return crewRepository.findAll().stream()
                .map(CrewDto::fromEntity)
                .toList();
    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteCrew(Long crewId) {
        CrewEntity crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 크루가 존재하지 않습니다."));
        crewRepository.delete(crew); // Soft Delete: @SQLDelete에 의해 is_deleted = true 처리됨
    }

}
