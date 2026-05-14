package com.users.book.dto;

public record CreateComments(
    Integer BookId,
    String Description,
    Integer Rating
){}
