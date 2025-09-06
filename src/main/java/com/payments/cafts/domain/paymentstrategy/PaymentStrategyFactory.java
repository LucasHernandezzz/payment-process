package com.payments.cafts.domain.paymentstrategy;

import com.payments.cafts.domain.model.PaymentType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentStrategyFactory {

    private final Map<PaymentType, PaymentStrategy> strategyMap = new EnumMap<>(PaymentType.class);

    public PaymentStrategyFactory(List<PaymentStrategy> strategies) {
        for (PaymentStrategy strategy : strategies) {
            if (strategy instanceof CreditCardPayment) {
                strategyMap.put(PaymentType.CREDIT_CARD, strategy);
            } else if (strategy instanceof PixPayment) {
                strategyMap.put(PaymentType.PIX, strategy);
            }
        }
    }

    public PaymentStrategy getStrategy(PaymentType paymentType) {
        PaymentStrategy strategy = strategyMap.get(paymentType);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
        return strategy;
    }
}
