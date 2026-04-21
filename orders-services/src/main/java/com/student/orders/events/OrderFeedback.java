package com.student.orders.events;

public record OrderFeedback(
    Integer isPriority,
    String isContext,
    Boolean isValid
) {}
