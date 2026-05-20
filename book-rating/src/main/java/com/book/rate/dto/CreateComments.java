package com.book.rate.dto;

public record CreateComments(
    Integer BookId,
    Integer UserId,
    String Description,
    Integer Rating
){}
