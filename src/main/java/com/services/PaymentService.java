package com.services;

import com.entity.PaymentEntity;

public interface PaymentService {

    PaymentEntity approvePayment(Long cardId, Long orderId);

    PaymentEntity getPaymentByOrderId(Long orderId);

    PaymentEntity getPaymentById(Long id);
}
