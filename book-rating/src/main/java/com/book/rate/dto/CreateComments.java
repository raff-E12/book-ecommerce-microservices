package com.book.rate.dto;

public record CreateComments(
    Integer BookId,
    String Description,
    Integer Rating
){}
