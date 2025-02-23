package com.drive.file_storage.model.repo;

import com.drive.file_storage.model.entity.FileMetadataCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataCacheRepository extends CrudRepository<FileMetadataCache, Long> {
}

