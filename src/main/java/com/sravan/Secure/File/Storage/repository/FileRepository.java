package com.sravan.Secure.File.Storage.repository;

import com.sravan.Secure.File.Storage.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
