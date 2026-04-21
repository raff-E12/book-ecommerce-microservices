package com.notification.services.events;

public record OrderFeedback(
    Integer isPriority,
    String isContext,
    Boolean isValid
) {}

