package com.student.orders.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import com.student.orders.dto.Checkout;
import com.student.orders.model.CheckOutModel;

@Mapper(componentModel = "spring")
public interface MappersGlobals {

    Checkout toDtoCheck(CheckOutModel book);
    List<Checkout> toDtoListCheck(List<CheckOutModel> books);
    CheckOutModel toEntityCheck(Checkout dto);
}
