package com.services.impl;

import com.entity.CardEntity;
import com.entity.OrderEntity;
import com.entity.PaymentEntity;
import com.repository.CardRepository;
import com.repository.OrderRepository;
import com.repository.PaymentRepository;
import com.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public PaymentEntity approvePayment(Long cardId, Long orderId) {

        CardEntity cardEntity = cardRepository.findById(cardId).orElse(null);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCardEntity(cardEntity);
        paymentEntity.setStatus("APPROVED");

        paymentRepository.save(paymentEntity);

        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        orderEntity.setPaymentEntity(paymentEntity);
        orderRepository.save(orderEntity);

        return paymentEntity;
    }
}
