package com.users.book.dto;

public record Rate(
    Integer Id,
    Integer BookId,
    String Description,
    Integer Vote,
    boolean Checked
) {}
