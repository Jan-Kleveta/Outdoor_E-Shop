package cz.cvut.fel.sit.service;

import cz.cvut.fel.sit.entity.Price;
import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.repository.PriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Service for managing prices associated with products.
 */
@Slf4j
@Service
@AllArgsConstructor
public class PriceService {

    /**
     * Repository for accessing price data.
     */
    private final PriceRepository priceRepository;

    /**
     * Service for managing products.
     */
    private final ProductService productService;


    /**
     * Creates a new price entry for a specified product.
     *
     * @param productId ID of the product to associate with the price.
     * @param price     Price object containing price information.
     * @return The created {@link Price} object.
     * @throws IllegalArgumentException if a price with the same currency already exists for the product.
     * @throws NullPointerException     if the productId or price is null.
     */
    @Transactional
    public Price createPrice(Long productId, Price price) {
        Objects.requireNonNull(productId, "Product ID cannot be null");
        Objects.requireNonNull(price, "Price cannot be null");
        Product product = productService.getProductById(productId);
        if (product.getPrices().stream().anyMatch(p -> p.getCurrency().equals(price.getCurrency()))) {
            throw new IllegalArgumentException("Price with this currency already exists for the product");
        }
        price.setId(null);
        price.setProduct(product);
        Price createdPrice = priceRepository.save(price);
        product.addPrice(createdPrice);
        log.info("Created price: {}", createdPrice);
        return createdPrice;
    }

    /**
     * Updates an existing price for a specified product.
     *
     * @param productId ID of the product to associate with the updated price.
     * @param price     Price object containing updated price information.
     * @return The updated {@link Price} object.
     * @throws IllegalArgumentException if the price does not exist or the product association is invalid.
     * @throws NullPointerException     if the price is null.
     */
    @Transactional
    public Price updatePrice(Long productId, Price price) {
        Objects.requireNonNull(price, "Price cannot be null");
        Price existingPrice = priceRepository.findById(price.getId())
                .orElseThrow(() -> new IllegalArgumentException("Price not found with ID: " + price.getId()));
        Product product = productService.getProductById(productId);
        Product existingProduct = productService.getProductById(existingPrice.getProduct().getId());
        existingPrice.setPrice(price.getPrice());
        existingPrice.setCurrency(price.getCurrency());
        existingPrice.setProduct(product);
        if (!existingProduct.equals(product)) {
            existingProduct.removePrice(existingPrice);
            product.addPrice(existingPrice);
        }
        log.info("Updated price: {}", existingPrice);
        return existingPrice;
    }
}
