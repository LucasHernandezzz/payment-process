package com.payments.cafts.domain.paymentstrategy;

import com.payments.cafts.domain.model.Payment;

public interface PaymentStrategy {

    void processPayment(Payment payment);
}
