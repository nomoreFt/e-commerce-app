package com.nomoreft.ecommerce.payment;

import com.nomoreft.ecommerce.customer.CustomerResponse;
import com.nomoreft.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}

