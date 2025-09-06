package com.payments.cafts.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_payment_credit_card")
public class PaymentCreditCard {

    @Id
    @Column(name = "payment_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "holder")
    private String holder;

    @Column(name = "expiration")
    private LocalDate expiration;

    @Column(name = "installments")
    private Integer installments;

}

