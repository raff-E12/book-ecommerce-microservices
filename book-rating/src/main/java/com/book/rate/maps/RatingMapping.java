package com.book.rate.maps;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.book.rate.dto.Rate;
import com.book.rate.dto.User;
import com.book.rate.models.RateModel;
import com.book.rate.models.UserModel;

@Mapper(componentModel = "spring")
public interface RatingMapping {

    @Mapping(source = "id", target = "UserID")
    @Mapping(source = "nomeCompleto", target = "Name")
    @Mapping(source = "verified",     target = "Verified")
    User toUserDto(UserModel userModel);

    @Mapping(source = "id",      target = "Id")
    @Mapping(source = "utente",      target = "User")
    @Mapping(source = "libro.id",    target = "BookId")
    @Mapping(source = "descrizione", target = "Description")
    @Mapping(source = "voto",        target = "Vote")
    @Mapping(source = "checked",     target = "Checked")
    Rate toDto(RateModel rateModel);
}
