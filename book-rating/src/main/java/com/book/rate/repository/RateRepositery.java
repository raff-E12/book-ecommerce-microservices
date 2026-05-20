package com.book.rate.repository;

import com.book.rate.models.RateModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RateRepositery extends JpaRepository<RateModel, Integer> {
    List<RateModel> findByLibroId(Integer libroId);
    List<RateModel> findByUtenteId(Integer utenteId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RateModel r WHERE r.id = :id")
    int deleteByIdNative(@Param("id") Integer id);
}
