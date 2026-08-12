package com.sravan.Secure.File.Storage.repository;

import com.sravan.Secure.File.Storage.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByStorageId(String storageId);

    Optional<FileEntity> findByStorageIdAndUserUsername(
            String storageId,
            String username
    );
}