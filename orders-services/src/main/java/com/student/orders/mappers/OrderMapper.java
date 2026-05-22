package com.student.orders.mappers;

import org.apache.kafka.common.metrics.stats.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.student.orders.dto.Books;
import com.student.orders.dto.CheckComplete;
import com.student.orders.dto.User;
import com.student.orders.model.BooksModels;
import com.student.orders.model.OrdersModel;
import com.student.orders.model.UserModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    @Mapping(source = "id",           target = "UserID")
    @Mapping(source = "nomeCompleto", target = "Name")
    @Mapping(source = "verified",     target = "Verified")
    @Mapping(source = "email",        target = "Email")
    @Mapping(source = "role",         target = "Role")
    User toUserDto(UserModel userModel);

    @Mapping(source = "id",        target = "Id")
    @Mapping(source = "titolo",    target = "BookTitle")
    @Mapping(source = "prezzo",    target = "Price")
    @Mapping(target = "Quanity",   ignore = true)
    @Mapping(target = "SubTotal",  ignore = true)
    Books toBookDto(BooksModels book);

    @Mapping(source = "id",           target = "Id")
    @Mapping(source = "prezzoTotale", target = "TotalPrice")
    @Mapping(source = "ordinato",     target = "Order")
    @Mapping(source = "utente",       target = "User")
    @Mapping(target = "BookList",     ignore = true)
    CheckComplete toCheckDto(OrdersModel orderModel);
}
