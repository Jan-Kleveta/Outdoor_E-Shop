package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.Size;
import cz.cvut.fel.sit.enums.VAT;
import cz.cvut.fel.sit.enums.VisibilityStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithSize extends IdEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Size size;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private double weight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
