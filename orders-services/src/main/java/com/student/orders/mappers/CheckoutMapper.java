package com.student.orders.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.student.orders.dto.Checkout;
import com.student.orders.global.interfaces.CheckOutQuery;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CheckoutMapper {

    @Mapping(source = "bookName", target = "BookName")
    @Mapping(source = "orderId", target = "OrderId")
    @Mapping(source = "price", target = "Price")
    @Mapping(source = "quantity", target = "Quantity")
    @Mapping(source = "subTotal", target = "SubTotal")
    Checkout map(CheckOutQuery query);

    List<Checkout> map(List<CheckOutQuery> queries);
}