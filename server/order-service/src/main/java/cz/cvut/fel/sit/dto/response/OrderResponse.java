package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.Currency;
import cz.cvut.fel.sit.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Order response", name = "OrderResponse")
public class OrderResponse {

    @Schema(description = "Timestamp of order creation")
    private LocalDateTime timestamp;

    @Schema(description = "Customer's email", example = "jannovak@example.com")
    private String email;

    @Schema(description = "Order number", example = "ORD-100000")
    private String orderNumber;

    @Schema(description = "Total price", example = "100.00")
    private BigDecimal totalPrice;

    @Schema(description = "Currency", example = "eur")
    private Currency currency;

    @Schema(description = "Order status", example = "RECEIVED")
    private OrderStatus status;

    @Schema(description = "Billing address")
    private BillingAddressResponse billingAddressResponse;

    @Schema(description = "Shipping address")
    private ShippingAddressResponse shippingAddressResponse;

    @Schema(description = "Shipping method")
    private ShippingMethodResponse shippingMethodResponse;

    @Schema(description = "List of product instances")
    private List<ProductInstanceResponse> productInstanceList = new ArrayList<>();
}
