package com.clb.cliembing.user.controller;

import com.clb.cliembing.user.dto.UserDto;
import com.clb.cliembing.user.entity.UserEntity;
import com.clb.cliembing.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRest {


    private final UserService userService;

    @GetMapping(value = "/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/insert")
    public Long addUser(@RequestBody UserDto.UserInsertDto userInsertDto) {

        return userService.insertUser(userInsertDto);
    }


    @GetMapping(value = "/name/{userId}")
    public UserEntity getUserByUserId(@PathVariable String userId) {
        return userService.getUserByUserId(userId);
    }

}
