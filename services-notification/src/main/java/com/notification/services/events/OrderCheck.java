package com.notification.services.events;

// Gestione Evento con Kafka
public record OrderCheck(
    Integer id,
    Double totalPrice,
    Boolean isPaid
){}
