package com.drive.file_storage.repository;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class FileStorageService {

    private final AmazonS3 s3Client;
    private FileService fileService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public FileStorageService(AmazonS3 s3Client, FileService fileService) {
        this.s3Client = s3Client;
        this.fileService = fileService;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
        String fileUrl = s3Client.getUrl(bucketName, fileName).toString(); // Returning the file URL

        fileService.saveFileMetadata(fileName, fileUrl);

        return fileUrl;
    }

    public S3Object downloadFile(String fileName) {
        return s3Client.getObject(bucketName, fileName);
    }
}
