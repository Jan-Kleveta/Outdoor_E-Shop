package cz.cvut.fel.sit.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.LineItem;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionRetrieveParams;
import cz.cvut.fel.sit.entity.*;
import cz.cvut.fel.sit.enums.*;
import cz.cvut.fel.sit.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class OrderService {

    @Value("${stripe.secret-key}")
    private String stripeSecret;
    private final String stripeWebhookKey;
    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(@Value("${stripe.webhook-key}") String stripeWebhookKey, OrderRepository orderRepository) {
        this.stripeWebhookKey = stripeWebhookKey;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecret;
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        Objects.requireNonNull(orderId, "Order ID must not be null.");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order with id " + orderId + " does not exist."));

        log.info("Fetched order: {}", order);
        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrdersByUserId(Long userId) {
        Objects.requireNonNull(userId, "User ID must not be null.");
        List<Order> orders = orderRepository.findAllByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("No orders found for user with id " + userId));

        log.info("Fetched orders for user with id {}: {}", userId, orders);
        return orders;

    }

    //@Async
    @Transactional
    public void verifyPayload(String sigHeader, String payload){
        Objects.requireNonNull(sigHeader, "Stripe signature header must not be null.");
        Objects.requireNonNull(payload, "Payload must not be null.");
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, stripeWebhookKey);
        } catch (SignatureVerificationException e) {
            log.error("Stripe signature verification failed: {}", e.getMessage());
            throw new RuntimeException("Stripe signature verification failed", e);
        } catch (Exception e) {
            log.error("Failed to parse Stripe webhook payload: {}", e.getMessage());
            throw new RuntimeException("Failed to parse Stripe webhook payload", e);
        }

        log.info("Received Stripe event: {}", event.getType());

        switch (event.getType()) {
            case "checkout.session.completed":
                Session session = (Session) event.getDataObjectDeserializer()
                        .getObject()
                        .orElse(null);

                if (session != null) {
                    log.info("Checkout session extracted: {}", session.toJson());
                    createOrder(session);
                } else {
                    log.warn("Session is null for event type: {}", event.getType());
                }
                break;
            default:
                log.warn("Unhandled event type: {}", event.getType());
        }
    }

    @Transactional
    public void createOrder(Session session) {
        Objects.requireNonNull(session, "Session must not be null.");
        Order order = new Order();
        order.setTimestamp(LocalDateTime.now());
        order.setOrderNumber(session.getMetadata().get("order-number"));
        //order.setUserId(Long.valueOf(session.getClientReferenceId()));
        order.setEmail(session.getCustomerDetails().getEmail());
        order.setTotalPrice(BigDecimal.valueOf(session.getAmountTotal() / 100L));
        order.setCurrency(Currency.valueOf(session.getCurrency()));
        order.setStatus(OrderStatus.RECEIVED);

        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setCarrier(Carrier.PACKETA);
        shippingMethod.setPrice(BigDecimal.valueOf(100));
        order.setShippingMethod(shippingMethod);

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setName(session.getCustomerDetails().getName());
        billingAddress.setCity(session.getCustomerDetails().getAddress().getCity());
        billingAddress.setCountry(Country.valueOf(session.getCustomerDetails().getAddress().getCountry()));
        billingAddress.setAddressLine1(session.getCustomerDetails().getAddress().getLine1());
        billingAddress.setAddressLine2(session.getCustomerDetails().getAddress().getLine2());
        billingAddress.setZipCode(session.getCustomerDetails().getAddress().getPostalCode());
        order.setBillingAddress(billingAddress);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setLocalNumber(session.getCustomerDetails().getPhone());

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setName(session.getCollectedInformation().getShippingDetails().getName());
        shippingAddress.setCity(session.getCollectedInformation().getShippingDetails().getAddress().getCity());
        shippingAddress.setCountry(Country.valueOf(session.getCollectedInformation().getShippingDetails().getAddress().getCountry()));
        shippingAddress.setAddressLine1(session.getCollectedInformation().getShippingDetails().getAddress().getLine1());
        shippingAddress.setAddressLine2(session.getCollectedInformation().getShippingDetails().getAddress().getLine2());
        shippingAddress.setZipCode(session.getCollectedInformation().getShippingDetails().getAddress().getPostalCode());
        shippingAddress.setPhoneNumber(phoneNumber);
        order.setShippingAddress(shippingAddress);

        SessionRetrieveParams params = SessionRetrieveParams.builder().
                addExpand("line_items")
                //.addExpand("data.price.product")
                .build();
        //const line_items = await stripe.checkout.sessions.listLineItems(session_id, {expand: ['data.price.product'],});

        Session retrivedSession;
        try {
            retrivedSession = Session.retrieve(session.getId(), params, null);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to retrieve session", e);
        }
        //log.info("Retrieved session: {}", retrivedSession.toJson());


        retrivedSession.getLineItems().getData().forEach(lineItem -> {
            //log.info("Line item: {}", lineItem.toJson());

            ProductInstance productInstance = new ProductInstance();
            //productInstance.setProductWithSizeId(Long.valueOf(lineItem.getPrice().getMetadata().get(("product_size_id"))));
            productInstance.setOrderedQuantity(Math.toIntExact(lineItem.getQuantity()));
            productInstance.setPricePerUnit(BigDecimal.valueOf(lineItem.getPrice().getUnitAmount() / 100L));
            productInstance.setCurrency(Currency.valueOf(lineItem.getPrice().getCurrency()));
            productInstance.setProductName(lineItem.getDescription());
            //productInstance.setProductSize(Size.valueOf(lineItem.getPrice().getMetadata().get("size")));
            productInstance.setOrder(order);
            order.addProductInstance(productInstance);


        });

        orderRepository.save(order);
        log.info("Order created: {}", order.toString());
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(String email) {
        Objects.requireNonNull(email, "User email must not be null.");
        List<Order> orders = orderRepository.findAllByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("No orders found for uemail " + email));

        log.info("Fetched orders for email {}: {}", email, orders);
        return orders;
    }


}
