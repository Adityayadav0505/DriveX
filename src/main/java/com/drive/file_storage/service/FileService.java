package com.drive.file_storage.service;

import com.drive.file_storage.model.entity.FileMetadata;
import com.drive.file_storage.model.entity.FileMetadataCache;
import com.drive.file_storage.model.repo.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class FileService {
    private final FileMetadataRepository fileMetadataRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cache.expiration.seconds:600}") // Default: 10 min
    private long cacheExpiration;

    public FileService(FileMetadataRepository repository, RedisTemplate<String, Object> redisTemplate) {
        this.fileMetadataRepository = repository;
        this.redisTemplate = redisTemplate;
    }

    public Long saveFileMetadata(String fileName, String url) {
        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(fileName);
        metadata.setUrl(url);
        metadata.setUploadDate(new Date());

        FileMetadata savedMetadata = fileMetadataRepository.save(metadata);

        FileMetadataCache cacheMetadata = new FileMetadataCache(metadata.getId(), fileName, url, metadata.getUploadDate());

        String redisKey = "FileMetadata:" + savedMetadata.getId();
        redisTemplate.opsForValue().set(redisKey, cacheMetadata, cacheExpiration, TimeUnit.SECONDS);

        return savedMetadata.getId();
    }

    @Cacheable(value = "FileMetadata", key = "#id")
    public Optional<FileMetadataCache> getFileMetadata(Long id) {
        String redisKey = "FileMetadata:" + id;

        // Try fetching from Redis first
        FileMetadataCache cachedMetadata = (FileMetadataCache) redisTemplate.opsForValue().get(redisKey);
        if (cachedMetadata != null) {
            return Optional.of(cachedMetadata);
        }

        // If not found in Redis, fetch from DB and store in Redis
        Optional<FileMetadata> dbMetadata = fileMetadataRepository.findById(id);
        if (dbMetadata.isPresent()) {
            FileMetadata file = dbMetadata.get();
            FileMetadataCache cache = new FileMetadataCache(file.getId(), file.getFileName(), file.getUrl(), file.getUploadDate());

            // Store in Redis with expiration
            redisTemplate.opsForValue().set(redisKey, cache, cacheExpiration, TimeUnit.SECONDS);

            return Optional.of(cache);
        }

        return Optional.empty();
    }
}
