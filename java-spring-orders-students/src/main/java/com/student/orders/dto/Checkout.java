package com.student.orders.dto;

import java.math.BigDecimal;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

public record Checkout(
    Integer id,
    String BookName,
    Integer OderId,
    BigDecimal Price,
    Integer Quantity,
    BigDecimal SubTotal
) {}
