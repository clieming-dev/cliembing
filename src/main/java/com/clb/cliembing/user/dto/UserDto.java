package com.clb.cliembing.user.dto;

import com.clb.cliembing.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    @Data
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor
    public static class UserInsertDto{

        private String userId;
        private String userName;
        private String email;
        private String password;
        private String profileImagePath;
        private String mbti;
        private Character gender;
        private String birth;
        private String mainPicId;


        public UserEntity toUserEntity(UserDto.UserInsertDto userInsertDto){
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userInsertDto.getUserId());
            userEntity.setUserName(userInsertDto.getUserName());
            userEntity.setEmail(userInsertDto.getEmail());
            userEntity.setPassword(userInsertDto.getPassword());
            userEntity.setProfileImagePath(userInsertDto.getProfileImagePath());
            userEntity.setMbti(userInsertDto.getMbti());
            userEntity.setGender(userInsertDto.getGender());
            userEntity.setMainPicId(userInsertDto.getMainPicId());
            return userEntity;
        }

    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor
    public static class UserOutDto{

        private Long id;
        private String userId;
        private String userName;
        private String email;
        private String password;
        private String profileImagePath;
        private String mbti;
        private Character gender;
        private String birth;
        private String mainPicId;

        public UserOutDto fromEntity(UserEntity userEntity){

            UserOutDto userOutDto = new UserOutDto();
            userOutDto.setUserId(userEntity.getUserId());
            userOutDto.setId(userEntity.getId());
            userOutDto.setUserName(userEntity.getUserName());
            userOutDto.setEmail(userEntity.getEmail());
            userOutDto.setProfileImagePath(userEntity.getProfileImagePath());
            userOutDto.setMbti(userEntity.getMbti());
            userOutDto.setGender(userEntity.getGender());
            userOutDto.setBirth(userEntity.getBirth());
            userOutDto.setMainPicId(userOutDto.getMainPicId());
            return userOutDto;
        }
    }
}
