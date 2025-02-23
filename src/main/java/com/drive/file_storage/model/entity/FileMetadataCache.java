package com.drive.file_storage.model.entity;

import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;
import java.util.Date;

@RedisHash("FileMetadata")
public class FileMetadataCache implements Serializable {

    private Long id;
    private String fileName;
    private String url;
    private Date uploadDate;

    public FileMetadataCache() {};

    public FileMetadataCache(Long id, String fileName, String url, Date uploadDate) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.uploadDate = uploadDate;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Date getUploadDate() { return uploadDate; }
    public void setUploadDate(Date uploadDate) { this.uploadDate = uploadDate; }
}