package com.clb.cliembing.crew.controller;

import com.clb.cliembing.crew.dto.CrewManageDto;
import com.clb.cliembing.crew.service.CrewManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "크루 검색 & 가입", description = "크루 검색 & 가입")
@RequestMapping("/api/crews/manage")
@RequiredArgsConstructor
public class CrewManageRest {

    private final CrewManageService crewManageService;

    @GetMapping
    @Operation(
            summary = "크루를 검색할수 있는 API",
            description = "크루를 검색할 수 있습니다. keyword는 Like 검색이고 sort없을경우 최신값입니다. (size는 선택, 기본 10)"
    )
    public Page<CrewManageDto.CrewSearchOutDto> searchCrew(
            @ParameterObject CrewManageDto.CrewSearchInDto crewSearchInDto,
        @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return crewManageService.searchCrew(crewSearchInDto, pageable);
    }



    @GetMapping("/detail")
    @Operation(
            summary = "크루를 상세보기할수 있는 API",
            description = "크루를 상세보기 할 수 있습니다."
    )
    public CrewManageDto.CrewDetailOutDto searchDetailCrew(@Valid @ParameterObject CrewManageDto.CrewDetailInDto crewDetailInDto){
        return crewManageService.getCrewInfo(crewDetailInDto);
    }

    @PostMapping("/join")
    @Operation(
            summary = "크루에 가입할 수 있는 API",
            description = "크루에 가입 할 수 있습니다."
    )
    public ResponseEntity<Void> joinCrew(@Valid @RequestBody CrewManageDto.JoinCrewInDto crewDetailInDto){
        crewManageService.joinCrew(crewDetailInDto);
        return ResponseEntity.ok().build();
    }
}
