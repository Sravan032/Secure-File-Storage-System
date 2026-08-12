package com.sravan.Secure.File.Storage.Controller;

import com.sravan.Secure.File.Storage.Service.FileService;
import com.sravan.Secure.File.Storage.dto.FileDownloadResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            Authentication authentication) throws Exception {

        String username = authentication.getName();

        return fileService.uploadFile(file, username);
    }

    @GetMapping("/download/{storageId}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable String storageId,
            Authentication authentication) throws Exception {

        String username = authentication.getName();

        FileDownloadResponse response =
                fileService.downloadFile(
                        storageId,
                        username
                );

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                response.getContentType()
                        )
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + response.getFileName()
                                + "\""
                )
                .body(response.getData());
    }
}