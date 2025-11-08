package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.Carrier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ShippingMethod extends IdEntity{

    @Column(nullable = false)
    private BigDecimal price;

    @Column//(nullable = false)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Carrier carrier;
}
