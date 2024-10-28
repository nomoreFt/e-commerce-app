package com.nomoreft.ecommerce.orderline;

public record OrderLineResponse(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {
}
