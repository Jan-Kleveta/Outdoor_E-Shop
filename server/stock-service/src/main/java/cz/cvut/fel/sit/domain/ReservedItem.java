package cz.cvut.fel.sit.domain;

import cz.cvut.fel.sit.enums.Currency;
import cz.cvut.fel.sit.enums.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReservedItem {

    private Long productId;
    private Long sizeId;
    private int quantity;
    private String name;
    private BigDecimal price;
    private Currency currency;
    private Size size;

}
