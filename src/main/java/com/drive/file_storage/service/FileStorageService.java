package com.drive.file_storage.service;

import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    public Long uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
        String fileUrl = s3Client.getUrl(bucketName, fileName).toString(); // Returning the file URL

        //fileService.saveFileMetadata(fileName, fileUrl);

        return fileService.saveFileMetadata(fileName, fileUrl);
    }

    public S3Object downloadFile(String fileName) {
        return s3Client.getObject(bucketName, fileName);
    }

    public List<String> listFiles() {
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result result = s3Client.listObjectsV2(request);

        return result.getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)  // Extract file names
                .collect(Collectors.toList());
    }
}
