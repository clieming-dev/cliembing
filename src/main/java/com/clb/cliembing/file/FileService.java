package com.clb.cliembing.file;

import com.clb.cliembing.file.entity.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileService {

    public FileDto.AllInfo saveFile(MultipartFile file){

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            FileTypeEnum fileType = FileTypeEnum.fromExtension(extension);

        return null;
    }
}
