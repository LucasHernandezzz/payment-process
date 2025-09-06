package com.payments.cafts.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_payment_pix")
public class PaymentPix {

    @Id
    @Column(name = "payment_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "pix_key")
    private String pixKey;

    @Column(name = "pix_key_type")
    private String pixKeyType;

}

