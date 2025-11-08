package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.VAT;
import cz.cvut.fel.sit.enums.VisibilityStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product extends IdEntity{


    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisibilityStatus visibilityStatus;

    @Enumerated
    @Column(nullable = false)
    private VAT vat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<ProductPhoto> productPhotos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<ProductWithSize> productsWithSize = new ArrayList<>();

        /*
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

     */

    public void addPrice(Price price) {
        if (price != null) {
            prices.add(price);
        }
    }

    public void removePrice(Price price) {
        if (price != null) {
            prices.remove(price);
        }
    }

    public void addProductPhoto(ProductPhoto productPhoto) {
        if (productPhoto != null) {
            productPhotos.add(productPhoto);
        }
    }

    public void removeProductPhoto(ProductPhoto productPhoto) {
        if (productPhoto != null) {
            productPhotos.remove(productPhoto);
        }
    }

    public void addProductWithSize(ProductWithSize productWithSize) {
        if (productWithSize != null) {
            productsWithSize.add(productWithSize);
        }
    }

    public void removeProductWithSize(ProductWithSize productWithSize) {
        if (productWithSize != null) {
            productsWithSize.remove(productWithSize);
        }
    }
}
