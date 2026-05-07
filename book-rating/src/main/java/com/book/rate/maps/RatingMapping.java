package com.book.rate.maps;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.book.rate.dto.Rate;
import com.book.rate.models.RateModel;

@Mapper(componentModel = "spring")
public interface RatingMapping {

    @Mapping(source = "id",          target = "Id")
    @Mapping(source = "libro.id",    target = "BookId")
    @Mapping(source = "descrizione", target = "Description")
    @Mapping(source = "voto",        target = "Vote")
    @Mapping(source = "checked",     target = "Checked")
    Rate toRate(RateModel rate);

    List<Rate> toRates(List<RateModel> rates);
}
