package com.student.orders.dto;

import java.util.List;

public record CheckComplete(
    Integer Id,
    List<Books> BookList,
    Double TotalPrice,
    User User,
    Boolean Order
){}
