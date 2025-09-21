package com.clb.cliembing.crew.service;


import com.clb.cliembing.crew.dto.CrewManageDto;
import com.clb.cliembing.crew.repository.CrewRepository;
import com.clb.cliembing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewManageService {

    private final CrewRepository crewRepository;
    private final UserRepository userRepository;

    public Page<CrewManageDto.CrewSearchOutDto> searchCrew(CrewManageDto.CrewSearchInDto crewSearchInDto, Pageable pageable) {
        List<CrewManageDto.CrewSearchOutDto> content = new ArrayList<>();
        CrewManageDto.CrewSearchOutDto crewSearchOutDto = new CrewManageDto.CrewSearchOutDto();
        crewSearchOutDto.setCrewId(1L);
        crewSearchOutDto.setCrewName("클라이밍");
        crewSearchOutDto.setActivityScore(4.7F);
        crewSearchOutDto.setDescription("양지웅이 짱입니다");
        crewSearchOutDto.setMemberCount(49);
        crewSearchOutDto.setPreferredAge("30대");
        crewSearchOutDto.setRegion("영등포구");
        content.add(crewSearchOutDto);
        return new PageImpl<>(content, pageable, 1);
    }
}
