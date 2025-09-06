package com.payments.cafts.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "user_id")
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "data_created")
    private LocalDateTime createdAt;

    @Column(name = "data_updated")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private PaymentCreditCard creditCardDetails;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private PaymentPix pixDetails;

}
