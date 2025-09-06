package com.payments.cafts.domain.paymentstrategy;

import com.payments.cafts.domain.model.Payment;
import com.payments.cafts.domain.model.PaymentStatus;
import com.payments.cafts.domain.util.Validators;
import com.payments.cafts.infrastructure.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("PIX")
@Slf4j
@RequiredArgsConstructor
public class PixPayment implements PaymentStrategy {

    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        log.info("Processing payment method pix");
        if (Validators.isValidPixKey(payment.getPixDetails().getPixKey(), payment.getPixDetails().getPixKeyType()))
        //futuramente enviar para o gateway de pagamento
        payment.setPaymentStatus(PaymentStatus.APPROVED);
        paymentRepository.save(payment);
    }
}
