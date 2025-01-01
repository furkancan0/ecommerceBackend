package com.furkan.ecommerce.payment;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String clientSecret;
    private String currency;
    private Long amount;
}