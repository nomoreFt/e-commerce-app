package com.nomoreft.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product ID is mandatory")
        Integer productId,
        @Positive(message = "Quantity must be positive")
        double quantity
) {
}
