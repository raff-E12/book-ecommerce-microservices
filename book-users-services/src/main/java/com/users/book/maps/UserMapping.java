package com.users.book.maps;

import org.mapstruct.Mapper;

import com.users.book.dto.User;
import com.users.book.models.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapping {
    User toDTO(UserModel user); 
    UserModel toEntity(User dto);
}
