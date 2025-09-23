package com.clb.cliembing.crew.controller;

import com.clb.cliembing.crew.dto.CrewMemberDto;
import com.clb.cliembing.crew.service.CrewMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crew-members")
@RequiredArgsConstructor
public class CrewMemberRest {
    private final CrewMemberService crewMemberService;

    /**
     * 크루 멤버 생성
     */
    @PostMapping
    public ResponseEntity<CrewMemberDto> createCrewMember(@RequestBody CrewMemberDto.CrewMemberCreateInDto createInDto) {
        CrewMemberDto created = crewMemberService.createCrewMember(createInDto);
        return ResponseEntity.ok(created);
    }

    /**
     * 특정 크루에 속한 모든 멤버 조회
     */
    @GetMapping("/crew/{crewId}")
    public ResponseEntity<List<CrewMemberDto.CrewMemberSearchOutDto>> getMembersByCrew(@PathVariable Long crewId) {
        List<CrewMemberDto.CrewMemberSearchOutDto> members = crewMemberService.getCrewMembersByCrewId(crewId);
        return ResponseEntity.ok(members);
    }

    /**
     * 특정 유저가 속한 모든 크루멤버 정보 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CrewMemberDto>> getMembershipsByUser(@PathVariable Long userId) {
        List<CrewMemberDto> memberships = crewMemberService.getCrewMembershipsByUserId(userId);
        return ResponseEntity.ok(memberships);
    }

    /**
     * 특정 유저가 특정 크루에 속해 있는지 확인
     */
    @GetMapping("/check")
    public ResponseEntity<CrewMemberDto> checkMembership(@RequestParam Long crewId, @RequestParam Long userId) {
        Optional<CrewMemberDto> membershipOpt = crewMemberService.findMembership(crewId, userId);
        return membershipOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 역할(role) 기반 크루 멤버 조회
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<CrewMemberDto.CrewMemberSearchOutDto>> getMembersByRole(@PathVariable String role) {
        List<CrewMemberDto.CrewMemberSearchOutDto> members = crewMemberService.getCrewMembersByRole(role);
        return ResponseEntity.ok(members);
    }
}
