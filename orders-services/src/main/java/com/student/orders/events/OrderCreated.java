package com.student.orders.events;

import java.math.BigDecimal;

public record OrderCreated(
    Integer id,
    String BookName,
    BigDecimal Price,
    Integer Quantity,
    BigDecimal SubTotal,
    Integer UserID
) {}
