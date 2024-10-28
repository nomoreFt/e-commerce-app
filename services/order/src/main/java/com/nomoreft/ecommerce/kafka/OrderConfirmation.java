package com.nomoreft.ecommerce.kafka;

import com.nomoreft.ecommerce.customer.CustomerResponse;
import com.nomoreft.ecommerce.order.PaymentMethod;
import com.nomoreft.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products
) {
}
