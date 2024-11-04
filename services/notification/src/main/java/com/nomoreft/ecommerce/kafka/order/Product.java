package com.nomoreft.ecommerce.kafka.order;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record Product(
        Integer productId,
        String name,
        String description,
        double quantity,
        BigDecimal price
) {
}
