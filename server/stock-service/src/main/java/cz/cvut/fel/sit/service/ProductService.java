package cz.cvut.fel.sit.service;

import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Service for managing products.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    /**
     * Repository for accessing product data.
     */
    private final ProductRepository productRepository;

    /**
     * Retrieves all products from the database.
     *
     * @return a list of all {@link Product} entities. If no products are found, returns an empty list.
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        List<Product> products = StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .toList();
        if (products.isEmpty()) {
            log.warn("No products found in the database.");
            return List.of();
        }
        log.info("Fetched all products: {}", products);
        return products;
    }

    /**
     * Creates a new product and saves it to the database.
     *
     * @param product the {@link Product} entity to be created.
     * @return the saved {@link Product} entity.
     * @throws IllegalArgumentException if the product already has an ID.
     * @throws NullPointerException     if the product is null.
     */
    @Transactional
    public Product createProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        if (product.getId() != null) {
            throw new IllegalArgumentException("Product ID must be null for new product");
        }
        product.setId(null);
        log.info("Creating product: {}", product);
        return productRepository.save(product);
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product the {@link Product} entity with updated information.
     * @return the updated {@link Product} entity.
     * @throws IllegalArgumentException if the product ID is null or the product is not found.
     * @throws NullPointerException     if the product is null.
     */
    @Transactional
    public Product updateProduct(Product product){
        Objects.requireNonNull(product, "Product cannot be null");
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null for update");
        }
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + product.getId()));
        existingProduct.setDescription(product.getDescription());
        existingProduct.setName(product.getName());
        existingProduct.setVisibilityStatus(product.getVisibilityStatus());
        existingProduct.setVat(product.getVat());
        log.info("Updated product: {}", existingProduct);
        return existingProduct;
    }

    /**
     * Retrieves a product by its ID from the database.
     *
     * @param id the ID of the product to retrieve.
     * @return the {@link Product} entity found with the specified ID.
     * @throws IllegalArgumentException if the product with the specified ID is not found.
     * @throws NullPointerException     if the product ID is null.
     */
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        Objects.requireNonNull(id, "Product ID cannot be null");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        log.info("Fetched product by ID: {}", product);
        return product;
    }


}
