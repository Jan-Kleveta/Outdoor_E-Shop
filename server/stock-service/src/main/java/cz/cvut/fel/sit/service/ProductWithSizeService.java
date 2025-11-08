package cz.cvut.fel.sit.service;

import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.entity.ProductWithSize;
import cz.cvut.fel.sit.repository.ProductWithSizeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Service for managing product sizes.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProductWithSizeService {

    /**
     * Service for managing products.
     */
    private final ProductService productService;

    /**
     * Repository for accessing product with size data.
     */
    private final ProductWithSizeRepository productWithSizeRepository;

    /**
     * Creates a new size entry for a specified product.
     *
     * @param productId        ID of the product to associate with the size.
     * @param productWithSize  {@link ProductWithSize} entity containing size information.
     * @return The created {@link ProductWithSize} object.
     * @throws IllegalArgumentException if the productId or size information is null.
     * @throws NullPointerException     if the productId or productWithSize is null.
     */
    @Transactional
    public ProductWithSize createSize(Long productId, ProductWithSize productWithSize) {
        Objects.requireNonNull(productId, "Product ID must not be null");
        Objects.requireNonNull(productWithSize, "Size must not be null");
        Product product = productService.getProductById(productId);
        productWithSize.setId(null);
        productWithSize.setProduct(product);
        ProductWithSize createdProductWithSize = productWithSizeRepository.save(productWithSize);
        product.addProductWithSize(createdProductWithSize);
        log.info("Created product with size: {}", createdProductWithSize);
        return createdProductWithSize;
    }

    /**
     * Updates an existing size entry for a specified product.
     *
     * @param productId        ID of the product associated with the size.
     * @param productWithSize  {@link ProductWithSize} entity containing updated size information.
     * @return The updated {@link ProductWithSize} object.
     * @throws IllegalArgumentException if the size ID does not exist or if the product ID is invalid.
     * @throws NullPointerException     if the productWithSize is null.
     */
    @Transactional
    public ProductWithSize updateSize(Long productId, ProductWithSize productWithSize) {
        Objects.requireNonNull(productWithSize, "Product with size must not be null");
        ProductWithSize existingProductWithSize = productWithSizeRepository.findById(productWithSize.getId())
                .orElseThrow(() -> new IllegalArgumentException("Size not found with ID: " + productWithSize.getId()));
        Product product = productService.getProductById(productId);
        Product existingProduct = productService.getProductById(existingProductWithSize.getProduct().getId());
        existingProductWithSize.setSize(productWithSize.getSize());
        existingProductWithSize.setStockQuantity(productWithSize.getStockQuantity());
        existingProductWithSize.setWeight(productWithSize.getWeight());
        existingProductWithSize.setProduct(product);
        if (!existingProduct.equals(product)) {
            existingProduct.removeProductWithSize(existingProductWithSize);
            product.addProductWithSize(existingProductWithSize);
        }
        log.info("Updated product with size: {}", existingProductWithSize);
        return existingProductWithSize;
    }
}
