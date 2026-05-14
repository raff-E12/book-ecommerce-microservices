package com.users.book.dto;

import java.math.BigDecimal;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

public record Checkout(
    Integer id,
    String BookName,
    Integer OrderId,
    BigDecimal Price,
    Integer Quantity,
    BigDecimal SubTotal,
    String CoverColor,
    String Editor,
    String Author
) {}
