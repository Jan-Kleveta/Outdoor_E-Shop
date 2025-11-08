package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.mapper.OrderMapper;
import cz.cvut.fel.sit.dto.response.OrderResponse;
import cz.cvut.fel.sit.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Orders", description = "API for order operations")
@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class OrderController {


    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @PreAuthorize("permitAll()")
    @Operation(summary = "Create order", description = "Creates order")
    @ApiResponse(responseCode = "200", description = "Order request successfully received")
    @PostMapping("/public/orders/stripe")
    public ResponseEntity<String> handleStripeEvent(
            @RequestHeader("Stripe-Signature") String sigHeader,
            @RequestBody String payload
    ) {
        orderService.verifyPayload(sigHeader, payload);
        return ResponseEntity.ok("Received");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        log.info("Request to get order received by controller: {}", id);
        OrderResponse orderResponse = orderMapper.toResponse(orderService.getOrderById(id));
        return ResponseEntity.ok(orderResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("public/orders/user")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@AuthenticationPrincipal Jwt principal) {
        log.info("Request to get orders by user received by controller: {}", principal.getSubject());
        List<OrderResponse> orderResponses = orderMapper.toResponseList(orderService.getOrdersByUser(principal.getSubject()));
        return ResponseEntity.ok(orderResponses);
    }
}
