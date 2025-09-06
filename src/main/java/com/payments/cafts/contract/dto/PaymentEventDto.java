package com.payments.cafts.contract.dto;

import com.payments.cafts.domain.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEventDto {
    private Long id;
    private PaymentType paymentType;
    private Double amount;
}
