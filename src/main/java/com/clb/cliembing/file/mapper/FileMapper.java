package com.clb.cliembing.file.mapper;


import com.clb.cliembing.file.dto.FileDto;
import com.clb.cliembing.file.entity.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    // Entity → DTO
    FileDto toDto(FileEntity entity);

    // DTO → Entity
    FileEntity toEntity(FileDto dto);


    FileDto.infoDto toInfoDto(FileEntity entity);

}