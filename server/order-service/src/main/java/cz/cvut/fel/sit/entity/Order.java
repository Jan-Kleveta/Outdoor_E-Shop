package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.Currency;
import cz.cvut.fel.sit.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shop-order")
public class Order extends IdEntity{

    @Column
    private Long userId;

    @Column//(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)//, optional = false)
    @JoinColumn(name = "billing_address_id")//, nullable = false)
    private BillingAddress billingAddress;

    @OneToOne(cascade = CascadeType.ALL)//, optional = false)
    @JoinColumn(name = "shipping_address_id")//, nullable = false)
    private ShippingAddress shippingAddress;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "shipping_method_id", nullable = false)
    private ShippingMethod shippingMethod;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<ProductInstance> productInstanceList = new ArrayList<>();

    public void addProductInstance(ProductInstance productInstance) {
        productInstanceList.add(productInstance);
        productInstance.setOrder(this);
    }

    public void removeProductInstance(ProductInstance productInstance) {
        productInstanceList.remove(productInstance);
        productInstance.setOrder(null);
    }

    public void setProductInstanceList(List<ProductInstance> productInstanceList) {
        this.productInstanceList.clear();
        if (productInstanceList != null) {
            for (ProductInstance productInstance : productInstanceList) {
                addProductInstance(productInstance);
            }
        }
    }
}

