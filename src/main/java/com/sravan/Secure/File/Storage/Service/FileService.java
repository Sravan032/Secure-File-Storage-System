package com.sravan.Secure.File.Storage.Service;

import com.sravan.Secure.File.Storage.Security.AESUtil;
import com.sravan.Secure.File.Storage.Security.RSAUtil;
import com.sravan.Secure.File.Storage.dto.FileDownloadResponse;
import com.sravan.Secure.File.Storage.model.FileEntity;
import com.sravan.Secure.File.Storage.model.StorageMetadata;
import com.sravan.Secure.File.Storage.model.User;
import com.sravan.Secure.File.Storage.repository.FileRepository;
import com.sravan.Secure.File.Storage.repository.UserRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final AESUtil aesUtil;
    private final RSAUtil rsaUtil;
    private final SecureStorageService secureStorageService;

    public FileService(FileRepository fileRepository,
                       UserRepository userRepository,
                       AESUtil aesUtil,
                       RSAUtil rsaUtil,
                       SecureStorageService secureStorageService) {

        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.aesUtil = aesUtil;
        this.rsaUtil = rsaUtil;
        this.secureStorageService = secureStorageService;
    }

    public String uploadFile(MultipartFile file, String username)
            throws Exception {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String storageId = UUID.randomUUID().toString();

        Path storageDirectory = null;

        try {

            byte[] originalBytes = file.getBytes();

            SecretKey aesKey = aesUtil.generateKey();

            byte[] encryptedFile =
                    aesUtil.encrypt(originalBytes, aesKey);

            byte[] encryptedKey =
                    rsaUtil.encryptAESKey(aesKey);

            storageDirectory =
                    secureStorageService.createStorageDirectory(storageId);

            secureStorageService.saveEncryptedFile(
                    storageDirectory,
                    encryptedFile
            );

            secureStorageService.saveEncryptedKey(
                    storageDirectory,
                    encryptedKey
            );

            StorageMetadata metadata = new StorageMetadata(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize(),
                    "AES-256",
                    "RSA-2048",
                    LocalDateTime.now().toString()
            );

            secureStorageService.saveMetadata(
                    storageDirectory,
                    metadata
            );

            FileEntity entity = new FileEntity();

            entity.setStorageId(storageId);
            entity.setFileName(file.getOriginalFilename());
            entity.setFileSize(file.getSize());
            entity.setContentType(file.getContentType());
            entity.setUploadedAt(LocalDateTime.now());
            entity.setUser(user);

            fileRepository.save(entity);

            return "File uploaded successfully";

        } catch (Exception e) {

            if (storageDirectory != null) {
                secureStorageService.deleteStorage(storageDirectory);
            }

            throw new RuntimeException(
                    "Failed to upload file.",
                    e
            );
        }
    }

    public FileDownloadResponse downloadFile(String storageId,
                                             String username)
            throws Exception {

        FileEntity fileEntity =
                fileRepository
                        .findByStorageIdAndUserUsername(
                                storageId,
                                username
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "File not found"
                                )
                        );

        byte[] encryptedKey =
                secureStorageService.readEncryptedKey(storageId);

        SecretKey aesKey =
                rsaUtil.decryptAESKey(encryptedKey);

        byte[] encryptedFile =
                secureStorageService.readEncryptedFile(storageId);

        byte[] decryptedFile =
                aesUtil.decrypt(encryptedFile, aesKey);

        return new FileDownloadResponse(
                decryptedFile,
                fileEntity.getFileName(),
                fileEntity.getContentType()
        );
    }
}