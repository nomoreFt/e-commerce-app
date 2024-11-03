package com.nomoreft.ecommerce.notification;

import com.nomoreft.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotifiicationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}
