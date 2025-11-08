package cz.cvut.fel.sit.service;

import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.entity.ProductPhoto;
import cz.cvut.fel.sit.repository.ProductPhotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Service for managing product photos.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProductPhotoService {

    /**
     * Repository for accessing product photo data.
     */
    private final ProductService productService;

    /**
     * Repository for accessing product photo data.
     */
    private final ProductPhotoRepository productPhotoRepository;

    /**
     * Creates a new product photo for a specified product.
     *
     * @param productId   ID of the product to associate with the photo.
     * @param productPhoto ProductPhoto object containing photo information.
     * @return The created {@link ProductPhoto} object.
     * @throws IllegalArgumentException if a photo with the same path already exists for the product.
     * @throws NullPointerException     if the productId or productPhoto is null.
     */
    @Transactional
    public ProductPhoto createPhoto(Long productId, ProductPhoto productPhoto) {
        Objects.requireNonNull(productId, "Product ID cannot be null");
        Objects.requireNonNull(productPhoto, "Product photo cannot be null");
        Product product = productService.getProductById(productId);
        productPhoto.setId(null);
        productPhoto.setProduct(product);
        ProductPhoto createdProductPhoto = productPhotoRepository.save(productPhoto);
        product.addProductPhoto(createdProductPhoto);
        log.info("Created product photo: {}", createdProductPhoto);
        return createdProductPhoto;
    }

    /**
     * Updates an existing product photo for a specified product.
     *
     * @param productId   ID of the product to associate with the updated photo.
     * @param productPhoto ProductPhoto object containing updated photo information.
     * @return The updated {@link ProductPhoto} object.
     * @throws IllegalArgumentException if the photo does not exist or if the product ID is invalid.
     * @throws NullPointerException     if the productId or productPhoto is null.
     */
    @Transactional
    public ProductPhoto updatePhoto(Long productId, ProductPhoto productPhoto) {
        Objects.requireNonNull(productPhoto, "Product photo cannot be null");
        ProductPhoto existingProductPhoto = productPhotoRepository.findById(productPhoto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Photo not found with ID: " + productPhoto.getId()));
        Product product = productService.getProductById(productId);
        Product existingProduct = productService.getProductById(existingProductPhoto.getProduct().getId());
        existingProductPhoto.setDescription(productPhoto.getDescription());
        existingProductPhoto.setPhotoType(productPhoto.getPhotoType());
        existingProductPhoto.setPath(productPhoto.getPath());
        existingProductPhoto.setProduct(product);
        if (!existingProduct.equals(product)) {
            existingProduct.removeProductPhoto(existingProductPhoto);
            product.addProductPhoto(existingProductPhoto);
        }
        log.info("Updated product photo: {}", existingProductPhoto);
        return existingProductPhoto;
    }
}
