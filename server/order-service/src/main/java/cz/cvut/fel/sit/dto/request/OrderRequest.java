package cz.cvut.fel.sit.dto.request;

import cz.cvut.fel.sit.entity.BillingAddress;
import cz.cvut.fel.sit.entity.ProductInstance;
import cz.cvut.fel.sit.entity.ShippingAddress;
import cz.cvut.fel.sit.entity.ShippingMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderRequest {

    @NotNull(message = "User's ID can't be null")
    @Schema(description = "User's ID", example = "2")
    private Long userId;

    @Valid
    @NotNull(message = "User billing address must be filled")
    @Schema(description = "User's billing address")
    private BillingAddress billingAddress;

    @Valid
    @NotNull(message = "User shipping address must be filled")
    @Schema(description = "User's shipping address")
    private ShippingAddress shippingAddress;

    @Valid
    @NotNull(message = "Shipping method must be filled")
    @Schema(description = "Shipping method")
    private ShippingMethod shippingMethod;

    @Valid
    @NotNull(message = "User status must be filled")
    @Schema(description = "Ordered product instances")
    private List<ProductInstance> productInstanceList;
}
