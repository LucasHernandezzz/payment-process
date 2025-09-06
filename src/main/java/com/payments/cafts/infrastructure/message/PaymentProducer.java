package com.payments.cafts.infrastructure.message;

import com.payments.cafts.contract.dto.PaymentEventDto;
import com.payments.cafts.contract.dto.PaymentRequestDto;
import com.payments.cafts.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendPaymentEvent(PaymentEventDto payment) {
        rabbitTemplate.convertAndSend("payment.process", payment);
    }
}
