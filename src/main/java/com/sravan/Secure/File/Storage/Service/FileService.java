package com.sravan.Secure.File.Storage.Service;

import com.sravan.Secure.File.Storage.model.FileEntity;
import com.sravan.Secure.File.Storage.model.User;
import com.sravan.Secure.File.Storage.repository.FileRepository;
import com.sravan.Secure.File.Storage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository,
                       UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public String uploadFile(MultipartFile file, String username) throws IOException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Path uploadPath = Paths.get("uploads", username);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();

        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        Path filePath = uploadPath.resolve(uniqueFileName);

        file.transferTo(filePath);

        try {

            FileEntity fileEntity = new FileEntity(
                    originalFileName,
                    filePath.toString(),
                    file.getSize(),
                    file.getContentType(),
                    LocalDateTime.now(),
                    user
            );

            fileRepository.save(fileEntity);

        } catch (Exception e) {

            Files.deleteIfExists(filePath);

            throw new RuntimeException("Failed to upload file.", e);
        }

        return "File uploaded successfully";
    }
}