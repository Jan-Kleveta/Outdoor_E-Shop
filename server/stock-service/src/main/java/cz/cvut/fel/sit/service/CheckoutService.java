package cz.cvut.fel.sit.service;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import cz.cvut.fel.sit.domain.ReservedItem;
import cz.cvut.fel.sit.dto.response.StripeSessionResponse;
import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.util.OrderNumberGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service for managing checkout processes, including creating Stripe sessions and reserving items.
 */
@Slf4j
@Service
public class CheckoutService {

    /**
     * Service for managing products.
     */
    private final ProductService productService;
    /**
     * Generator for creating unique order numbers.
     */
    private final OrderNumberGenerator orderNumberGenerator;
    /**
     * Secret key for Stripe API.
     */
    private final String stripeSecretKey;

    private final String hostURL;

    /**
     * Constructor for CheckoutService.
     *
     * @param stripeSecretKey Secret key for Stripe API.
     * @param productService Service for managing products.
     * @param orderNumberGenerator Generator for order numbers.
     */
    public CheckoutService(@Value("${HOST_URL}") String hostURL, @Value("${stripe.secret-key}") String stripeSecretKey, ProductService productService, OrderNumberGenerator orderNumberGenerator) {
        this.hostURL = hostURL;
        this.productService = productService;
        this.stripeSecretKey = stripeSecretKey;
        this.orderNumberGenerator = orderNumberGenerator;
    }

    /**
     * Creates a Stripe session for checkout.
     *
     * @param items List of items to be reserved and purchased.
     * @param principal JWT principal representing the user.
     * @return A response containing the Stripe session URL.
     * @throws IllegalArgumentException if items are null or empty.
     * @throws RuntimeException if Stripe customer or session creation fails.
     */
    @Transactional
    public StripeSessionResponse createStripeSession(List<ReservedItem> items, Jwt principal){
        List<ReservedItem> reservedItems = reserveItems(items);
        Stripe.apiKey = stripeSecretKey;
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items cannot be null or empty");
        }
        List<SessionCreateParams.LineItem> lineItems = reservedItems.stream().map(item ->
                SessionCreateParams.LineItem.builder()
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(item.getCurrency().toString())
                                        .setUnitAmount(item.getPrice().multiply(BigDecimal.valueOf(100)).longValue())
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getName() + " " + item.getSize())
                                                        .putMetadata("product_size_id", item.getSizeId().toString())
                                                        .build()
                                        )
                                        .build()
                        )
                        .setQuantity((long) item.getQuantity())
                        .build()
        ).toList();



        String orderNumber = orderNumberGenerator.generateOrderNumber();
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(hostURL+"order/success/"+orderNumber)
                .setCancelUrl(hostURL + "order/cancel")
                .addAllLineItem(lineItems)
                .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                .setShippingAddressCollection(SessionCreateParams.ShippingAddressCollection.builder()
                        .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.CZ)
                        .build())
                .setPhoneNumberCollection(
                        SessionCreateParams.PhoneNumberCollection.builder()
                                .setEnabled(true)
                                .build()
                )
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .putMetadata("order-number", orderNumber)
                .addShippingOption(
                        SessionCreateParams.ShippingOption.builder()
                                .setShippingRateData(
                                        SessionCreateParams.ShippingOption.ShippingRateData.builder()
                                                .setDisplayName("Packeta")
                                                .setType(SessionCreateParams.ShippingOption.ShippingRateData.Type.FIXED_AMOUNT)
                                                .setFixedAmount(
                                                        SessionCreateParams.ShippingOption.ShippingRateData.FixedAmount.builder()
                                                                .setAmount(50L)
                                                                .setCurrency("eur")
                                                                .build()
                                                )
                                                .setDeliveryEstimate(
                                                        SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.builder()
                                                                .setMinimum(
                                                                        SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Minimum.builder()
                                                                                .setUnit(SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Minimum.Unit.BUSINESS_DAY)
                                                                                .setValue(1L)
                                                                                .build()
                                                                )
                                                                .setMaximum(
                                                                        SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Maximum.builder()
                                                                                .setUnit(SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Maximum.Unit.BUSINESS_DAY)
                                                                                .setValue(3L)
                                                                                .build()
                                                                )
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );

        Customer customer = null;
        if (principal != null) {
            try {
                CustomerCreateParams params = CustomerCreateParams.builder()
                        .setEmail(principal.getSubject())
                        .build();
                customer = Customer.create(params);
                log.info("Stripe customer created successfully: {}", customer.getId());
            } catch (Exception e) {
                log.error("Error creating Stripe customer: {}", e.getMessage());
                throw new RuntimeException("Failed to create Stripe customer", e);
            }
        }
        if (customer != null) {
            paramsBuilder.setCustomer(customer.getId());
        }
        SessionCreateParams params = paramsBuilder.build();
        try {
            Session session = Session.create(params);
            log.info("Stripe session created successfully: {}", session.getUrl());
            return new StripeSessionResponse(session.getUrl());
        } catch (Exception e) {
            log.error("Error creating Stripe session: {}", e.getMessage());
            throw new RuntimeException("Failed to create Stripe session", e);
        }

    }

    /**
     * Reserves items in the inventory by reducing the available stock.
     *
     * @param items List of items to be reserved.
     * @return A list of successfully reserved items.
     * @throws IllegalArgumentException if not all items could be reserved.
     * @throws NullPointerException if the items list is null.
     */
    @Transactional
    public List<ReservedItem> reserveItems(List<ReservedItem> items) {
        List<ReservedItem> reservedItems = new ArrayList<>();
        Objects.requireNonNull(items, "Session request cannot be null");
        items.forEach(item -> {
                    Product product = productService.getProductById(item.getProductId());
                    product.getProductsWithSize().forEach(productWithSize -> {
                        if (productWithSize.getId().equals(item.getSizeId()) && productWithSize.getStockQuantity() >= item.getQuantity()){
                            productWithSize.setStockQuantity(productWithSize.getStockQuantity() - item.getQuantity());
                            item.setName(product.getName());
                            item.setSize(productWithSize.getSize());
                            item.setPrice(product.getPrices().stream()
                                    .filter(price -> price.getCurrency().equals(item.getCurrency()))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Price not found for currency: " + item.getCurrency()))
                                    .getPrice());
                            reservedItems.add(item);
                            log.info("Reserved {} items of product with size ID: {}, quantity left: {}",
                                    item.getQuantity(),
                                    item.getSizeId(),
                                    productWithSize.getStockQuantity());
                        }
                    });
                }
        );
        if (reservedItems.size() != items.size()) {
            log.warn("Not all items could be reserved. Reserved items: {}", reservedItems);
            throw new IllegalArgumentException("Not all items could be reserved");
        }
        return reservedItems;
    }

    /**
     * Restocks items back to the inventory in case of a failed transaction or cancellation.
     *
     * @param items List of items to be restocked.
     * @throws NullPointerException if the items list is null.
     */
    @Transactional
    public void restockItems(List<ReservedItem> items){
        Objects.requireNonNull(items, "Session request cannot be null");
        items.forEach(item -> {
            Product product = productService.getProductById(item.getProductId());
            product.getProductsWithSize().forEach(productWithSize -> {
                if (productWithSize.getId().equals(item.getSizeId())){
                    productWithSize.setStockQuantity(productWithSize.getStockQuantity() + item.getQuantity());
                    log.info("Restocked {} items of product with size ID: {}, quantity left: {}",
                            item.getQuantity(),
                            item.getSizeId(),
                            productWithSize.getStockQuantity());
                }
            });
        });
    }
}
