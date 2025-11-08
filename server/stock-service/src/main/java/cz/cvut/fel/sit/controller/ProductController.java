package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.mapper.ProductMapper;
import cz.cvut.fel.sit.dto.request.ProductRequest;
import cz.cvut.fel.sit.dto.response.ProductResponse;
import cz.cvut.fel.sit.service.ProductService;
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
public class ProductController {

    private final ProductMapper productMapper;

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        log.info("Request to create product received by controller: {}", productRequest);
        Product product = productMapper.toEntity(productRequest);
        Product createdProduct = productService.createProduct(product);
        ProductResponse productResponse = productMapper.toResponse(createdProduct);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(productResponse);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/public/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        log.info("Request to get product by id received by controller: {}", id);
        Product product = productService.getProductById(id);
        ProductResponse productResponse = productMapper.toResponse(product);
        return ResponseEntity.ok(productResponse);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/public/product")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("Request to get all products received by controller");
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productResponses = productMapper.toResponseList(products);
        return ResponseEntity.ok(productResponses);
    }

    //FIXME - product ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody ProductRequest productRequest) {
        log.info("Request to update product with id {} received by controller: {}", id, productRequest);
        Product product = productMapper.toEntity(productRequest);
        product.setId(id);
        Product updatedProduct = productService.updateProduct(product);
        ProductResponse productResponse = productMapper.toResponse(updatedProduct);
        return ResponseEntity.ok(productResponse);
    }


}
