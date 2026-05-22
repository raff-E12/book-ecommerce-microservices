package com.book.rate.dto;

public record Rate(
    Integer Id,
    Integer BookId,
    User User,
    String Description,
    Integer Vote,
    boolean Checked
) {}
