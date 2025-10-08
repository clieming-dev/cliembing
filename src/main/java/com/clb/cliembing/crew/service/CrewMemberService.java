package com.clb.cliembing.crew.service;

import com.clb.cliembing.crew.dto.CrewMemberDto;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.crew.entity.CrewMemberEntity;
import com.clb.cliembing.crew.repository.CrewMemberRepository;
import com.clb.cliembing.crew.repository.CrewRepository;
import com.clb.cliembing.gym.repository.GymRepository;
import com.clb.cliembing.user.entity.UserEntity;
import com.clb.cliembing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;
    private final CrewRepository crewRepository;
    private final UserRepository userRepository;

    /**
     * 크루 멤버 생성
     */
    public CrewMemberDto createCrewMember(CrewMemberDto.CrewMemberCreateInDto inDto) {
        CrewEntity crew = crewRepository.findById(inDto.getCrewId())
                .orElseThrow(() -> new IllegalArgumentException("크루가 존재하지 않습니다."));

        UserEntity user = userRepository.findById(inDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        CrewMemberEntity crewMember = CrewMemberEntity.builder()
                .crew(crew)
                .user(user)
                .role(inDto.getRole())
                .build();

        CrewMemberEntity saved = crewMemberRepository.save(crewMember);
        return CrewMemberDto.fromEntity(saved);
    }

    /**
     * 특정 크루에 속한 모든 멤버 조회
     */
    public List<CrewMemberDto.CrewMemberSearchOutDto> getCrewMembersByCrewId(Long crewId) {
        List<CrewMemberEntity> members = crewMemberRepository.findByCrew_Id(crewId);
        return members.stream()
                .map(CrewMemberDto::toOutDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 유저가 속한 모든 크루멤버 정보 조회
     */
    public List<CrewMemberDto> getCrewMembershipsByUserId(Long userId) {
        List<CrewMemberEntity> memberships = crewMemberRepository.findByUser_Id(userId);
        return memberships.stream()
                .map(CrewMemberDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 유저가 특정 크루에 속해 있는지 확인
     */
    public Optional<CrewMemberDto> findMembership(Long crewId, Long userId) {
        Optional<CrewEntity> crewOpt = crewRepository.findById(crewId);
        Optional<UserEntity> userOpt = userRepository.findById(userId);

        if (crewOpt.isEmpty() || userOpt.isEmpty()) return Optional.empty();

        return crewMemberRepository.findByCrewAndUser(crewOpt.get(), userOpt.get())
                .map(CrewMemberDto::fromEntity);
    }

    /**
     * 역할(role) 기반 크루 멤버 조회
     */
    public List<CrewMemberDto.CrewMemberSearchOutDto> getCrewMembersByRole(String role) {
        List<CrewMemberEntity> members = crewMemberRepository.findByRole(role);
        return members.stream()
                .map(CrewMemberDto::toOutDto)
                .collect(Collectors.toList());
    }

    /**
     * 크루 멤버 역할 변경
     * @param memberId 변경할 크루 멤버 ID
     * @param roleUpdateInDto 새로운 역할 정보 DTO
     * @return 변경된 크루 멤버 DTO
     * @throws IllegalArgumentException 존재하지 않는 멤버거나, 유효하지 않은 역할일 경우 예외 발생
     */
    public CrewMemberDto updateCrewMemberRole(Long memberId, CrewMemberDto.RoleUpdateInDto roleUpdateInDto) {
        CrewMemberEntity crewMember = crewMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("크루 멤버가 존재하지 않습니다."));

        // 역할 유효성 검사 예시 (선택사항)
        String newRole = roleUpdateInDto.getNewRole();
        if (newRole == null || newRole.isBlank()) {
            throw new IllegalArgumentException("새로운 역할은 비어 있을 수 없습니다.");
        }

        // 역할 변경
        crewMember.setRole(newRole);

        // 변경된 멤버 저장
        CrewMemberEntity updated = crewMemberRepository.save(crewMember);

        return CrewMemberDto.fromEntity(updated);
    }
}
