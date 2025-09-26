package com.manager.task_manager.Helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadHelper {

    public final String UPLOAD_DIR = new File("src/main/resources/static/image").getAbsolutePath();

    public boolean uploadFile(MultipartFile file, String fileName) {
        try {

            fileName = Paths.get(fileName).getFileName().toString();
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            Path fullPath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(fullPath, file.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    
}
