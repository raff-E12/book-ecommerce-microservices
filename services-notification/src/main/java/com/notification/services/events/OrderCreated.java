package com.notification.services.events;

import java.math.BigDecimal;

public record OrderCreated(
    Integer id,
    String BookName,
    Integer OrderId,
    BigDecimal Price,
    Integer Quantity,
    BigDecimal SubTotal
) {}
