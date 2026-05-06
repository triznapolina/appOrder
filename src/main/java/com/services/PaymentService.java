package com.services;

import com.entity.PaymentEntity;

public interface PaymentService {

    PaymentEntity approvePayment(Long cardId, Long orderId);
}
