package com.drive.file_storage.repository;

import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class FileService {
    private final FileMetadataRepository fileMetadataRepository;

    public FileService(FileMetadataRepository repository) {
        this.fileMetadataRepository = repository;
    }

    public void saveFileMetadata(String fileName, String url, String uploadedBy) {
        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(fileName);
        metadata.setUrl(url);
        metadata.setUploadedBy(uploadedBy);
        metadata.setUploadDate(new Date());
        fileMetadataRepository.save(metadata);
    }
}
