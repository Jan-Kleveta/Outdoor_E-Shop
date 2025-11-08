package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.PhotoType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductPhoto extends IdEntity{

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhotoType photoType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


}
