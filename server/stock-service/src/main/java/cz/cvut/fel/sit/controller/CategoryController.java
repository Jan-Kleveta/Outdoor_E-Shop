package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.entity.Category;
import cz.cvut.fel.sit.mapper.CategoryMapper;
import cz.cvut.fel.sit.dto.request.CategoryRequest;
import cz.cvut.fel.sit.dto.response.CategoryResponse;
import cz.cvut.fel.sit.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class CategoryController {

    private final CategoryMapper categoryMapper;

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Request to create category received by controller: {}", categoryRequest);
        Category category = categoryMapper.toEntity(categoryRequest);
        CategoryResponse categoryResponse = categoryMapper.toResponse(categoryService.createCategory(category));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).body(categoryResponse);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/public/category/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        log.info("Request to get category by id received by controller: {}", id);
        Category category = categoryService.getCategoryById(id);
        CategoryResponse categoryResponse = categoryMapper.toResponse(category);
        return ResponseEntity.ok(categoryResponse);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/public/category")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        log.info("Request to get all categories received by controller");
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
        return ResponseEntity.ok(categoryResponses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("Request to delete category by id received by controller: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/category/{categoryId}/products/{productId}")
    public ResponseEntity<CategoryResponse> addProductToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        log.info("Request to add product with id {} to category with id {} received by controller", productId, categoryId);
        Category category = categoryService.addProductToCategory(categoryId, productId);
        CategoryResponse categoryResponse = categoryMapper.toResponse(category);
        return ResponseEntity.ok(categoryResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/category/{categoryId}/products/{productId}")
    public ResponseEntity<CategoryResponse> removeProductFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        log.info("Request to remove product with id {} from category with id {} received by controller", productId, categoryId);
        Category category = categoryService.removeProductFromCategory(categoryId, productId);
        //CategoryResponse categoryResponse = categoryMapper.toResponse(category);
        return ResponseEntity.noContent().build();
    }


}
