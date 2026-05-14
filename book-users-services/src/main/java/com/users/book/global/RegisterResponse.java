package com.users.book.global;

public record RegisterResponse(
    String fullname,
    String email,
    String password,
    String role,
    Boolean verified
){}
