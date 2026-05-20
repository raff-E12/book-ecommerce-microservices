package com.book.rate.global;

public record RegisterResponse(
    String fullname,
    String email,
    String password,
    String role,
    Boolean verified
) {}
