package com.ricardofood.review.amqp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {
    private Long id;
    private BigDecimal amount;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private PaymentStatusDto status;
    private Long orderId;
    private String paymentMethod;

}
