package com.student.orders.dto;

import java.math.BigDecimal;

public record BookTableList(
    Integer libro_id,
    BigDecimal libro_prezzo,
    Integer quantita,
    BigDecimal prezzo_subtotale
){}
