package com.payments.cafts.domain.paymentstrategy;

import com.payments.cafts.domain.model.Payment;
import com.payments.cafts.domain.model.PaymentStatus;
import com.payments.cafts.domain.util.Validators;
import com.payments.cafts.infrastructure.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("CREDIT_CARD")
@Slf4j
public class CreditCardPayment implements PaymentStrategy {

    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        log.info("Processing payment method credit card");
        if(Validators.isValidCreditCard(payment.getCreditCardDetails().getCardNumber()));
        payment.setPaymentStatus(PaymentStatus.APPROVED);
        paymentRepository.save(payment);
    }
}
