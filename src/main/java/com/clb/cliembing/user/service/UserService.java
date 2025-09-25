package com.clb.cliembing.user.service;

import com.clb.cliembing.user.dto.UserDto;
import com.clb.cliembing.user.entity.UserEntity;
import com.clb.cliembing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity getUserById(Long id){
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new NoSuchElementException("검색하려는 User가 존재하지 않습니다.");
        }
        return optionalUser.get();
    }

    public Long insertUser(UserDto.UserInsertDto userInsertDto) {


        UserEntity userEntity = userRepository.save(userInsertDto.toUserEntity(userInsertDto));

        return userEntity.getId();
    }

    public UserEntity getUserByUserId(String userId) {
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new NoSuchElementException("검색하려는 User가 존재하지 않습니다.");
        }
        return optionalUser.get();
    }
}
