package com.clb.cliembing.file.repository;

import com.clb.cliembing.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

}
