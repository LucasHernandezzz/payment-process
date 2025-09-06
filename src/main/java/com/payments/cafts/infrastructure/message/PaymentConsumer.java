package com.payments.cafts.infrastructure.message;

import com.payments.cafts.domain.model.Payment;
import com.payments.cafts.domain.paymentstrategy.PaymentStrategy;
import com.payments.cafts.domain.paymentstrategy.PaymentStrategyFactory;
import com.payments.cafts.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;
    private final PaymentStrategyFactory strategyFactory;

    @RabbitListener(queues = "payment.process")
    public void receivePaymentId(Long paymentId) {
        log.info("Received payment ID for processing: {}", paymentId);
            Payment payment = paymentService.findById(paymentId);

            PaymentStrategy strategy = strategyFactory.getStrategy(payment.getPaymentType());

            strategy.processPayment(payment);
    }
}
