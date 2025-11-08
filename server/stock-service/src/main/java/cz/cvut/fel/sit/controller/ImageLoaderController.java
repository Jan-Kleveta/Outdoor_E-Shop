package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ImageLoaderController {

    private final ImageService imageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/images/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.saveImage(file));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/public/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getImage(filename));
    }

}

