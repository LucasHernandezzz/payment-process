package com.payments.cafts.contract.dto;

import com.payments.cafts.domain.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    private Double amount;
    private PaymentType paymentType;
    private String userId;

    private CreditCardDetailsDto creditCard;
    private PixDetailsDto pix;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditCardDetailsDto {
        private String cardNumber;
        private String cardHolder;
        private String cardExpiration;
        private String cardCvv;
        private Integer installments;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PixDetailsDto {
        private String pixKey;
        private String pixKeyType;
    }
}

