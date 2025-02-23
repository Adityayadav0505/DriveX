package com.drive.file_storage.controller;

import com.drive.file_storage.model.entity.FileMetadataCache;
import com.drive.file_storage.service.FileService;
import com.drive.file_storage.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileStorageService fileStorageService;
    private final FileService fileService;

    @Autowired
    public FileController(FileStorageService fileStorageService, FileService fileService) {
        this.fileStorageService = fileStorageService;
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        Long fileId = fileStorageService.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully, with ID : " + fileId);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) throws IOException {

        S3Object s3Object = fileStorageService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(s3Object.getObjectContent().readAllBytes());
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(){

        List<String> filename = fileStorageService.listFiles();
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/metadata/{id}")
    public ResponseEntity<?> getFileMetadata(@PathVariable Long id) {
        Optional<FileMetadataCache> metadata = fileService.getFileMetadata(id);

        if (metadata.isPresent()) {
            return ResponseEntity.ok(metadata.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File metadata not found.");
    }
}

