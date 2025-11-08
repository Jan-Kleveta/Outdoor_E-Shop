package cz.cvut.fel.sit.service;

import org.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Service for images in file system.
 */
@Slf4j
@Service
public class ImageService {

    /**
     * The root directory where images are stored.
     */
    private final Path root = Paths.get("uploads");


    /**
     * Saves an image to the server's local file system.
     *
     * @param file the image file to be saved.
     * @return the path to the saved image.
     * @throws RuntimeException if the file is empty or an error occurs during saving.
     */
    public String saveImage(MultipartFile file){
        if (file.isEmpty()) {
            log.error("Failed to upload image: File is empty");
            throw new RuntimeException("Failed to upload image: File is empty");
        }
        try {
            if (!Files.exists(root)) Files.createDirectories(root);
            String filename = /*UUID.randomUUID() + "_" + */ file.getOriginalFilename();
            Path filePath = root.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("File uploaded successfully: {}", filename);
            return filePath.toString();
        } catch (Exception e) {
            log.error("Failed to upload image: {}", e.getMessage());
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }
    }

    /**
     * Retrieves an image from the server's local file system.
     *
     * @param filename the name of the image file to be retrieved.
     * @return the image as a {@link Resource}.
     * @throws RuntimeException if the image cannot be found or read.
     */
    public Resource getImage(String filename) {
        try {
            Path file = root.resolve(filename);
            org.springframework.core.io.Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                log.info("File loaded successfully: {}", filename);
                return resource;
            } else {
                log.error("Could not read the file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.error("Failed to load image: {}", e.getMessage());
            throw new RuntimeException("Failed to load image: " + e.getMessage());
        }
    }
}
