package com.student.orders.dto;

public record Books(
    Integer Id,
    String BookTitle,
    Double Price,
    Integer Quanity,
    Double SubTotal
){}
