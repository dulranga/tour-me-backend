package com.tourme.services.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Local disk file storage implementation
 * Stores files on the server's local filesystem
 */
@Service
public class LocalFileStorageService implements FileStorageService {

    @Value("${app.storage.upload-dir:/uploads}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file, String directory) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        return uploadFile(file.getOriginalFilename(), file.getBytes(), directory);
    }

    @Override
    public String uploadFile(String filename, byte[] fileContent, String directory) throws Exception {
        // Create directory if it doesn't exist
        String fullPath = uploadDir + (directory != null ? "/" + directory : "");
        Path dirPath = Paths.get(fullPath);
        Files.createDirectories(dirPath);

        // Generate unique filename
        String uniqueFilename = UUID.randomUUID() + "_" + filename;
        Path filePath = dirPath.resolve(uniqueFilename);

        // Write file to disk
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(fileContent);
            fos.flush();
        }

        // Return relative path for storage in database
        return "/" + directory + "/" + uniqueFilename;
    }

    @Override
    public void deleteFile(String filePath) throws Exception {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }

        // Reconstruct full path
        String fullPath = uploadDir + filePath;
        Path path = Paths.get(fullPath);

        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
