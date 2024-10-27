package com.nomoreft.ecommerce.product;

import com.nomoreft.ecommerce.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private double availableQuantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public boolean hasSufficientQuantity(double quantity) {
        return this.availableQuantity >= quantity;
    }

    public void reduceQuantity(double quantity) {
        this.availableQuantity -= quantity;
    }
}
