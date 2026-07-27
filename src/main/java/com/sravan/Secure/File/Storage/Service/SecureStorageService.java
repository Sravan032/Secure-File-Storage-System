package com.sravan.Secure.File.Storage.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SecureStorageService {

    private static final String ROOT_DIRECTORY = "uploads";

    /**
     * Creates a new storage folder using storageId.
     */
    public Path createStorageDirectory(String storageId) throws IOException {

        Path storagePath = Paths.get(ROOT_DIRECTORY, storageId);

        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        return storagePath;
    }

    /**
     * Saves encrypted file.
     */
    public Path saveEncryptedFile(Path storagePath,
                                  byte[] encryptedFile) throws IOException {

        Path filePath = storagePath.resolve("file.enc");

        Files.write(filePath, encryptedFile);

        return filePath;
    }

    /**
     * Saves encrypted AES key.
     */
    public Path saveEncryptedKey(Path storagePath,
                                 byte[] encryptedKey) throws IOException {

        Path keyPath = storagePath.resolve("key.enc");

        Files.write(keyPath, encryptedKey);

        return keyPath;
    }

    /**
     * Deletes entire storage directory.
     */
    public void deleteStorage(Path storagePath) throws IOException {

        if (!Files.exists(storagePath)) {
            return;
        }

        Files.walk(storagePath)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}