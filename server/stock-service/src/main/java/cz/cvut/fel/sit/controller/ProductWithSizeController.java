package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.entity.ProductWithSize;
import cz.cvut.fel.sit.mapper.ProductWithSizeMapper;
import cz.cvut.fel.sit.dto.request.ProductWithSizeRequest;
import cz.cvut.fel.sit.dto.response.ProductWithSizeResponse;
import cz.cvut.fel.sit.service.ProductWithSizeService;
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
public class ProductWithSizeController {

    private final ProductWithSizeMapper productWithSizeMapper;

    private final ProductWithSizeService productWithSizeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product/{productId}/size")
    public ResponseEntity<ProductWithSizeResponse> createSize(@PathVariable Long productId,
                                                               @Valid @RequestBody ProductWithSizeRequest sizeRequest) {
        log.info("Request to create size for product with id {} received by controller: {}", productId, sizeRequest);
        ProductWithSize productWithSize = productWithSizeMapper.toEntity(sizeRequest);
        ProductWithSize createdSize = productWithSizeService.createSize(productId, productWithSize);
        ProductWithSizeResponse sizeResponse = productWithSizeMapper.toResponse(createdSize);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSize.getId())
                .toUri();

        return ResponseEntity.created(location).body(sizeResponse);
    }

    //FIXME - product ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{productId}/size/{id}")
    public ResponseEntity<ProductWithSizeResponse> updateSize(@PathVariable Long productId,
                                                              @PathVariable Long id,
                                                     @Valid @RequestBody ProductWithSizeRequest sizeRequest) {
        log.info("Request to update size with id {} received by controller: {}", id, sizeRequest);
        ProductWithSize productWithSize = productWithSizeMapper.toEntity(sizeRequest);
        productWithSize.setId(id);
        ProductWithSize updatedSize = productWithSizeService.updateSize(productId, productWithSize);
        ProductWithSizeResponse sizeResponse = productWithSizeMapper.toResponse(updatedSize);
        return ResponseEntity.ok(sizeResponse);
    }
}
