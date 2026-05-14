package com.users.book.dto;

public record User(
    Integer id,
    String nomeCompleto,
    String email,
    String role,
    Boolean verified
) {}