package com.book.rate.dto;

public record Rate(
    Integer Id,
    Integer BookId,
    String Description,
    Integer Vote,
    boolean Checked
) {}
