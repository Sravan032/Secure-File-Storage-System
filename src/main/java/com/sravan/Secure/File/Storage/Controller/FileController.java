package com.sravan.Secure.File.Storage.Controller;

import com.sravan.Secure.File.Storage.Service.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) throws IOException {

        return fileService.uploadFile(file, authentication.getName());
    }
}