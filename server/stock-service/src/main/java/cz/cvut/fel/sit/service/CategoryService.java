package cz.cvut.fel.sit.service;

import cz.cvut.fel.sit.entity.Category;
import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.repository.CategoryRepository;
import cz.cvut.fel.sit.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private void validateString(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    @Transactional
    public Category createCategory(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");

        log.info("Creating category: {}", category);

        category.setId(null);

        /*
        if (category.getSubcategories() != null) {
            category.getSubcategories().forEach(subcategory -> {
                subcategory.setParentCategory(category);
                subcategory.setId(null);
            });
        }

         */


        if (category.getParentCategory() != null) {
            category.getParentCategory().addSubcategory(category);
        }

        if (category.getProducts() != null) {
            category.getProducts().forEach( product -> {
                if (!productRepository.existsById(product.getId())){
                    category.removeProduct(product);
                    throw new IllegalArgumentException("Product with id " + product.getId() + " does not exist");
                }
            });
        }

        Category createdCategory = categoryRepository.save(category);
        log.info("Category created: {}", createdCategory);
        return createdCategory;
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        Objects.requireNonNull(id, "Category ID cannot be null");
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + id + " not found"));
        log.info("Retrieved category: {}", category);
        return category;
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        log.info("Retrieving all categories");
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        log.info("Retrieved categories: {}", categories);
        return categories;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Objects.requireNonNull(id, "Category ID cannot be null");
        Category category = getCategoryById(id);
        log.info("Deleting category: {}", category);
        if (category.getParentCategory() != null) {
            category.getParentCategory().removeSubcategory(category);
        }
        categoryRepository.delete(category);
        log.info("Category deleted: {}", category);
    }

    @Transactional
    public Category addProductToCategory(Long categoryId, Long productId) {
        Objects.requireNonNull(categoryId, "Category ID cannot be null");
        Objects.requireNonNull(productId, "Product ID cannot be null");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found"));
        if (category.getProducts().contains(product)) {
            log.warn("Product with ID {} is already in category with ID {}", productId, categoryId);
            return category;
        }
        log.info("Adding product with ID {} to category with ID {}", productId, categoryId);
        category.addProduct(product);
        categoryRepository.save(category);
        log.info("Product with ID {} added to category with ID {}", productId, categoryId);
        return category;
    }

    @Transactional
    public Category removeProductFromCategory(Long categoryId, Long productId) {
        Objects.requireNonNull(categoryId, "Category ID cannot be null");
        Objects.requireNonNull(productId, "Product ID cannot be null");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found"));
        if (!category.getProducts().contains(product)) {
            log.warn("Product with ID {} is not in category with ID {}", productId, categoryId);
            return category;
        }
        log.info("Removing product with ID {} from category with ID {}", productId, categoryId);
        category.removeProduct(product);
        categoryRepository.save(category);
        log.info("Product with ID {} removed from category with ID {}", productId, categoryId);
        return category;
    }
}
