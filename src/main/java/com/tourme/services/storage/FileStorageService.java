package com.tourme.services.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Abstract interface for file storage
 * Allows switching between local disk, S3, or other storage providers
 */
public interface FileStorageService {

    /**
     * Upload a file and return the storage URL/path
     * 
     * @param file      - The file to upload
     * @param directory - Subdirectory where file should be stored
     * @return The URL/path where the file is stored
     */
    String uploadFile(MultipartFile file, String directory) throws Exception;

    /**
     * Upload file from bytes
     * 
     * @param filename    - The filename
     * @param fileContent - File content as bytes
     * @param directory   - Subdirectory where file should be stored
     * @return The URL/path where the file is stored
     */
    String uploadFile(String filename, byte[] fileContent, String directory) throws Exception;

    /**
     * Delete a file from storage
     * 
     * @param filePath - The file path/URL to delete
     */
    void deleteFile(String filePath) throws Exception;
}
