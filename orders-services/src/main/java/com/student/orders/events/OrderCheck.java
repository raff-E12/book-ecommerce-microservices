package com.student.orders.events;

import java.math.BigDecimal;

public record OrderCheck(
    Integer id,
    BigDecimal totalPrice,
    Boolean isPaid
){}
