package com.payments.cafts.domain.service;

import com.payments.cafts.contract.dto.PaymentRequestDto;
import com.payments.cafts.domain.model.*;
import com.payments.cafts.domain.util.Validators;
import com.payments.cafts.infrastructure.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void processPayment(PaymentRequestDto paymentDto) {

        Payment payment = createPaymentFromDto(paymentDto);

        switch (paymentDto.getPaymentType()) {
            case CREDIT_CARD:
                PaymentCreditCard creditCardDetails = createCreditCardDetailsFromDto(paymentDto, payment);
                payment.setCreditCardDetails(creditCardDetails);
                break;
            case PIX:
                PaymentPix pixDetails = createPixDetailsFromDto(paymentDto, payment);
                payment.setPixDetails(pixDetails);
                break;
        }

        paymentRepository.save(payment);

        rabbitTemplate.convertAndSend("payment.process", payment.getId());
    }

    private Payment createPaymentFromDto(PaymentRequestDto paymentDto) {
        return Payment.builder()
                .userId(paymentDto.getUserId())
                .paymentType(paymentDto.getPaymentType())
                .amount(paymentDto.getAmount())
                .paymentStatus(PaymentStatus.PROCESSING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private PaymentCreditCard createCreditCardDetailsFromDto(PaymentRequestDto dto, Payment payment) {
        PaymentRequestDto.CreditCardDetailsDto cardDto = dto.getCreditCard();
        return PaymentCreditCard.builder()
                .payment(payment)
                .holder(cardDto.getCardHolder())
                .cardNumber(cardDto.getCardNumber())
                .installments(cardDto.getInstallments())
                .expiration(LocalDate.parse(cardDto.getCardExpiration()))
                .build();
    }

    private PaymentPix createPixDetailsFromDto(PaymentRequestDto dto, Payment payment) {
        PaymentRequestDto.PixDetailsDto pixDto = dto.getPix();
        return PaymentPix.builder()
                .payment(payment)
                .pixKey(pixDto.getPixKey())
                .pixKeyType(pixDto.getPixKeyType())
                .build();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}