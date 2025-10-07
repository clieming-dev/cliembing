package com.clb.cliembing.user.controller;

import com.clb.cliembing.crew.dto.CrewManageDto;
import com.clb.cliembing.user.dto.UserDto;
import com.clb.cliembing.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/manage")
public class UserManageRest {

//
//    private final UserService userService;
//
//    public Page<UserDto.> searchCrew(
//            @ParameterObject CrewManageDto.CrewSearchInDto crewSearchInDto,
//            @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
//    ){
//        return crewManageService.searchCrew(crewSearchInDto, pageable);
//    }
}
