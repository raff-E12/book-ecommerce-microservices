package com.book.crud.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.book.crud.dto.Book;
import com.book.crud.model.BookModel;

// Aggiunta della Repositery
@Repository
public interface BookRepositery extends JpaRepository<BookModel, Integer>  {
    List<BookModel> findAllById(Integer id);

    @Query(value = "SELECT * FROM public.libri", nativeQuery = true)
    List<BookModel> findAllNative();

    List<BookModel> findAllByTrashedFalse();

    List<BookModel> findAllByTrashedTrue();

    @Modifying
    @Transactional
    @Query("UPDATE BookModel b SET b.trashed = true WHERE b.id = :id")
    void trashById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE BookModel b SET b.trashed = false WHERE b.id = :id")
    void restoreById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE BookModel b SET b.Tashdate = :date WHERE b.id = :id")
    void setTrashDateById(@Param("id") Integer id, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE BookModel b SET b.Tashdate = null WHERE b.id = :id")
    void setTrashDateNullById(@Param("id") Integer id);

}
