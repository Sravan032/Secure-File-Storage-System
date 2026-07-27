package com.sravan.Secure.File.Storage.Service;

import com.sravan.Secure.File.Storage.Security.AESUtil;
import com.sravan.Secure.File.Storage.Security.RSAUtil;
import com.sravan.Secure.File.Storage.model.FileEntity;
import com.sravan.Secure.File.Storage.model.User;
import com.sravan.Secure.File.Storage.repository.FileRepository;
import com.sravan.Secure.File.Storage.repository.UserRepository;
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

    public String uploadFile(MultipartFile file, String username) throws Exception {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String storageId = UUID.randomUUID().toString();

        Path storageDirectory = null;

        try {

            // Read uploaded file
            byte[] originalBytes = file.getBytes();

            // Generate AES Key
            SecretKey aesKey = aesUtil.generateKey();

            // Encrypt File
            byte[] encryptedFile = aesUtil.encrypt(originalBytes, aesKey);

            // Encrypt AES Key
            byte[] encryptedKey = rsaUtil.encryptAESKey(aesKey);

            // Create storage directory
            storageDirectory = secureStorageService.createStorageDirectory(storageId);

            // Save encrypted file
            secureStorageService.saveEncryptedFile(storageDirectory, encryptedFile);

            // Save encrypted AES key
            secureStorageService.saveEncryptedKey(storageDirectory, encryptedKey);

            // Save metadata
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

            throw new RuntimeException("Failed to upload file.", e);
        }
    }
}