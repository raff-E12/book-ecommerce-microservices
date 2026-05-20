package com.book.rate.dto;

public record Rate(
    Integer Id,
    Integer BookId,
    Integer UserId,
    String Description,
    Integer Vote,
    boolean Checked
) {}
