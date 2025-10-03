package com.clb.cliembing.crew.controller;

import com.clb.cliembing.crew.dto.CrewMemberDto;
import com.clb.cliembing.crew.service.CrewMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crew-members")
@RequiredArgsConstructor
@Tag(name = "크루 멤버 관리", description = "크루 멤버 생성, 조회, 역할 변경, 추방 등 멤버 관리 API")
public class CrewMemberRest {

    private final CrewMemberService crewMemberService;

    @PostMapping
    @Operation(summary = "크루 멤버 생성", description = "크루에 멤버를 추가합니다.")
    public ResponseEntity<CrewMemberDto> createCrewMember(@Valid @RequestBody CrewMemberDto.CrewMemberCreateInDto createInDto) {
        CrewMemberDto created = crewMemberService.createCrewMember(createInDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/crew/{crewId}")
    @Operation(summary = "특정 크루 멤버 조회", description = "특정 크루에 속한 모든 멤버 목록을 조회합니다.")
    public ResponseEntity<List<CrewMemberDto.CrewMemberSearchOutDto>> getMembersByCrew(@PathVariable Long crewId) {
        List<CrewMemberDto.CrewMemberSearchOutDto> members = crewMemberService.getCrewMembersByCrewId(crewId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "특정 유저가 속한 크루 멤버십 조회", description = "특정 유저가 속한 모든 크루 멤버십 정보를 조회합니다. " +
            "// 멤버십 : 유저와 크루 간의 소속 관계 + 역할/상태 등의 부가 정보")

    public ResponseEntity<List<CrewMemberDto>> getMembershipsByUser(@PathVariable Long userId) {
        List<CrewMemberDto> memberships = crewMemberService.getCrewMembershipsByUserId(userId);
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/check")
    @Operation(summary = "유저-크루 멤버십 확인", description = "특정 유저가 특정 크루에 속해있는지 확인합니다.")
    public ResponseEntity<CrewMemberDto> checkMembership(@RequestParam Long crewId, @RequestParam Long userId) {
        Optional<CrewMemberDto> membershipOpt = crewMemberService.findMembership(crewId, userId);
        return membershipOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "역할 기반 크루 멤버 조회", description = "특정 역할을 가진 크루 멤버 목록을 조회합니다.")
    public ResponseEntity<List<CrewMemberDto.CrewMemberSearchOutDto>> getMembersByRole(@PathVariable String role) {
        List<CrewMemberDto.CrewMemberSearchOutDto> members = crewMemberService.getCrewMembersByRole(role);
        return ResponseEntity.ok(members);
    }

    // 추가 예정 기능: 멤버 역할 변경, 멤버 추방, 출석 현황 보기 API 등도
    // @Operation 어노테이션과 함께 여기에 추가 가능
}
