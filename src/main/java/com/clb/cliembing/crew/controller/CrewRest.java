package com.clb.cliembing.crew.controller;

import com.clb.cliembing.crew.dto.CrewDto;
import com.clb.cliembing.crew.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crews")
@RequiredArgsConstructor
public class CrewRest {

    private final CrewService crewService;

    // 크루 생성
    @PostMapping
    public ResponseEntity<Long> createCrew(@RequestBody CrewDto.CrewCreateInDto requestDto) {
        Long crewId = crewService.createCrew(requestDto);
        return ResponseEntity.ok(crewId);
    }

    // 단일 크루 조회
    @GetMapping("/{crewId}")
    public ResponseEntity<CrewDto> getCrew(@PathVariable Long crewId) {
        CrewDto crewDto = crewService.getCrew(crewId);
        return ResponseEntity.ok(crewDto);
    }

    // 전체 크루 목록 조회
    @GetMapping
    public ResponseEntity<List<CrewDto>> getAllCrews() {
        List<CrewDto> crewList = crewService.getAllCrews();
        return ResponseEntity.ok(crewList);
    }

    // 크루 삭제 (soft delete)
    @DeleteMapping("/{crewId}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Long crewId) {
        crewService.deleteCrew(crewId);
        return ResponseEntity.noContent().build();
    }
}
