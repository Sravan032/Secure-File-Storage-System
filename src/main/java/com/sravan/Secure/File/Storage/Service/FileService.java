package com.sravan.Secure.File.Storage.Service;

import com.sravan.Secure.File.Storage.model.FileEntity;
import com.sravan.Secure.File.Storage.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {
    private final FileRepository fileRepository;
    public FileService(FileRepository fileRepository){
        this.fileRepository=fileRepository;
    }

    public String Uploadfile(MultipartFile file,String username) throws IOException {
        String UploadDir = "D:/uploads/";
        File directory = new File(UploadDir);
        if(!directory.exists()){
            directory.mkdirs();
        }

        String FilePath = UploadDir+file.getOriginalFilename();
        file.transferTo(new File(FilePath));

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(file.getOriginalFilename());
        fileEntity.setFilepath(FilePath);
        fileEntity.setUserid(1L);
        fileRepository.save(fileEntity);

        return "File Uploaded Successfully..";
    }
}
