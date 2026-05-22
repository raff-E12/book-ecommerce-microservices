package com.student.orders.dto;

public record User(
    Integer UserID,
    String Name,
    Boolean Verified,
    String Email,
    String Role
){}
