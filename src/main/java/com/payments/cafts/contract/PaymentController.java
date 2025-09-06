package com.payments.cafts.contract;

import com.payments.cafts.contract.dto.PaymentRequestDto;
import com.payments.cafts.domain.model.Payment;
import com.payments.cafts.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity processPayment(@RequestBody PaymentRequestDto payment) {
        paymentService.processPayment(payment);
        return ResponseEntity.ok().build();
    }
}
