package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.entity.ProductPhoto;
import cz.cvut.fel.sit.mapper.ProductPhotoMapper;
import cz.cvut.fel.sit.dto.request.ProductPhotoRequest;
import cz.cvut.fel.sit.dto.response.ProductPhotoResponse;
import cz.cvut.fel.sit.service.ProductPhotoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ProductPhotoController {

    private final ProductPhotoMapper productPhotoMapper;

    private final ProductPhotoService productPhotoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product/{productId}/photo")
    public ResponseEntity<ProductPhotoResponse> createPhoto(@PathVariable Long productId,
                                                            @Valid @RequestBody ProductPhotoRequest photoRequest) {
        log.info("Request to create photo for product with id {} received by controller: {}", productId, photoRequest);
        ProductPhoto productPhoto = productPhotoMapper.toEntity(photoRequest);
        ProductPhoto createdProductPhoto = productPhotoService.createPhoto(productId, productPhoto);
        ProductPhotoResponse productPhotoResponse = productPhotoMapper.toResponse(createdProductPhoto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProductPhoto.getId())
                .toUri();

        return ResponseEntity.created(location).body(productPhotoResponse);
    }

    //FIXME - product ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{productId}/photo/{id}")
    public ResponseEntity<ProductPhotoResponse> updatePhoto(@PathVariable Long productId,
                                                            @PathVariable Long id,
                                                     @Valid @RequestBody ProductPhotoRequest photoRequest) {
        log.info("Request to update photo with id {} received by controller: {}", id, photoRequest);
        ProductPhoto productPhoto = productPhotoMapper.toEntity(photoRequest);
        productPhoto.setId(id);
        ProductPhoto updatedProductPhoto = productPhotoService.updatePhoto(productId, productPhoto);
        ProductPhotoResponse productPhotoResponse = productPhotoMapper.toResponse(updatedProductPhoto);
        return ResponseEntity.ok(productPhotoResponse);
    }

}

