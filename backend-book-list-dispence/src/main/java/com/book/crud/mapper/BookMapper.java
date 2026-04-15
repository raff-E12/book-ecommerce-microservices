package com.book.crud.mapper;

import com.book.crud.model.BookModel;
import com.book.crud.dto.Book;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring") 
public interface BookMapper {
    // Trasforma un singolo libro
    Book toDto(BookModel book);
    
    // Trasforma una lista di libri (utilissimo per la FindAll)
    List<Book> toDtoList(List<BookModel> books);

    // Trasforma il DTO di input in Entity per il database
    BookModel toEntity(Book dto);
}