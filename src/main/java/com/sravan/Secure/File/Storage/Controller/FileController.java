package com.sravan.Secure.File.Storage.Controller;

import com.sravan.Secure.File.Storage.Service.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping ("/api/files")
public class FileController {
    private final FileService fileService;
    public FileController(FileService fileService){
        this.fileService=fileService;
    }

    @PostMapping("/upload")
    public String UploadFile(@RequestParam("file")MultipartFile file, @RequestHeader("Authorization") String AuthHeader) throws IOException {
        String token = AuthHeader.substring(7);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("TOKEN: " + token);
        String username = auth.getName();

        return fileService.Uploadfile(file,username);
    }
}
