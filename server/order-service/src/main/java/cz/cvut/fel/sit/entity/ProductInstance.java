package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.Currency;
import cz.cvut.fel.sit.enums.Size;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "order")
@Entity
public class ProductInstance extends IdEntity{
    //TODO
    //@Column//(nullable = false)
    //private Long productWithSizeId;

    @Column(nullable = false)
    private String productName;

    //FIXME?
    //@Column(nullable = false)
    //private Size productSize;

    @Column(nullable = false)
    private int orderedQuantity;

    @Column(nullable = false)
    private BigDecimal pricePerUnit;

    //FIXME: currency probably redundant? part of order
    @Column(nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
