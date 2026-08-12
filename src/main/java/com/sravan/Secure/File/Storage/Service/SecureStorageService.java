package com.sravan.Secure.File.Storage.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sravan.Secure.File.Storage.model.StorageMetadata;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SecureStorageService {

    private static final String ROOT_DIRECTORY = "uploads";

    private final ObjectMapper objectMapper;

    public SecureStorageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Path createStorageDirectory(String storageId) throws IOException {

        Path storagePath = Paths.get(ROOT_DIRECTORY, storageId);

        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        return storagePath;
    }

    public Path saveEncryptedFile(Path storagePath,
                                  byte[] encryptedFile) throws IOException {

        Path filePath = storagePath.resolve("file.enc");

        Files.write(filePath, encryptedFile);

        return filePath;
    }

    public Path saveEncryptedKey(Path storagePath,
                                 byte[] encryptedKey) throws IOException {

        Path keyPath = storagePath.resolve("key.enc");

        Files.write(keyPath, encryptedKey);

        return keyPath;
    }

    public void saveMetadata(Path storagePath,
                             StorageMetadata metadata) throws IOException {

        Path metadataPath = storagePath.resolve("metadata.json");

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(metadataPath.toFile(), metadata);
    }

    public void deleteStorage(Path storagePath) throws IOException {

        if (!Files.exists(storagePath)) {
            return;
        }

        try (var paths = Files.walk(storagePath)) {

            paths.sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}