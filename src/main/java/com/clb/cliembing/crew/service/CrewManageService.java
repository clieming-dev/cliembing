package com.clb.cliembing.crew.service;


import com.clb.cliembing.crew.dto.CrewManageDto;
import com.clb.cliembing.crew.entity.CrewEntity;
import com.clb.cliembing.crew.repository.CrewRepository;
import com.clb.cliembing.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public CrewManageDto.CrewDetailOutDto getCrewInfo(CrewManageDto.CrewDetailInDto crewDetailInDto) {
        Optional<CrewEntity> crewEntity = crewRepository.findById(crewDetailInDto.getCrewId());

        if(crewEntity.isEmpty()){
            throw new NoSuchElementException("요청하신 CrewID가 전재하지 않습니다.");
        }
        return CrewManageDto.CrewDetailOutDto.fromEntity(crewEntity.get());
    }

    public void joinCrew(CrewManageDto.JoinCrewInDto joinCrewInDto){

    }

    public CrewManageDto.CrewDetailOutDto getCrew(Long crewId) {
        CrewEntity crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 크루가 존재하지 않습니다."));
        return CrewManageDto.CrewDetailOutDto.fromEntity(crew);
    }

    public void createCrew(CrewManageDto.CreateInDto createInDto) {

    }
}
