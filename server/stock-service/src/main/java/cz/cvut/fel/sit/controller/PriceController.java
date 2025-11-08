package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.entity.Price;
import cz.cvut.fel.sit.mapper.PriceMapper;
import cz.cvut.fel.sit.dto.request.PriceRequest;
import cz.cvut.fel.sit.dto.response.PriceResponse;
import cz.cvut.fel.sit.service.PriceService;
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
public class PriceController {

    private final PriceMapper priceMapper;

    private final PriceService priceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product/{productId}/price")
    public ResponseEntity<PriceResponse> createPrice(@PathVariable Long productId,
                                                       @Valid @RequestBody PriceRequest priceRequest) {
        log.info("Request to create price for product with id {} received by controller: {}", productId, priceRequest);
        Price price = priceMapper.toEntity(priceRequest);
        Price createdPrice = priceService.createPrice(productId, price);
        PriceResponse priceResponse = priceMapper.toResponse(createdPrice);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPrice.getId())
                .toUri();

        return ResponseEntity.created(location).body(priceResponse);
    }

    //FIXME - product ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{productId}/price/{id}")
    public ResponseEntity<PriceResponse> updatePrice(@PathVariable Long productId,
                                                     @PathVariable Long id,
                                                     @Valid @RequestBody PriceRequest priceRequest) {
        log.info("Request to update price with id {} received by controller: {}", id, priceRequest);
        Price price = priceMapper.toEntity(priceRequest);
        price.setId(id);
        Price updatedPrice = priceService.updatePrice(productId, price);
        PriceResponse priceResponse = priceMapper.toResponse(updatedPrice);
        return ResponseEntity.ok(priceResponse);
    }

    //TODO implement delete
}
