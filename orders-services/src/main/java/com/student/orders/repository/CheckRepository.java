package com.student.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.orders.dto.Checkout;
import com.student.orders.model.CheckOutModel;

import jakarta.transaction.Transactional;

@Repository
public interface CheckRepository extends JpaRepository<CheckOutModel, Integer> {

    @Query(value = """
        SELECT 
            c.id,
            l.titolo as BookName,
            c.ordine_id as OrderId,
            c.libro_prezzo as Price,
            c.quantita as Quantity,
            c.prezzo_subtotale as SubTotal,
            l.cover_color as CoverColor,
            l.editore as Editore,
            l.autore as Autore
        FROM public.checkout c
        INNER JOIN public.libri l ON c.libro_id = l.id
        WHERE c.ordine_id = :ordineId
        """, nativeQuery = true)
    List<Object[]> findAllConTitolo(@Param("ordineId") int ordineId); 
   
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.checkout WHERE ordine_id = :ordineId", nativeQuery = true)
    void deleteByOrdineId(@Param("ordineId") int ordineId);

    @Query(value = "SELECT * FROM public.checkout WHERE ordine_id = :ordineId", nativeQuery = true)
    List<CheckOutModel> findByOrdineId(@Param("ordineId") int ordineId);

    @Query(value = "SELECT * FROM public.checkout WHERE libro_id = :id", nativeQuery = true)
    CheckOutModel findByIdBook(@Param("id") int id);

    @Query("SELECT MAX(c.id) FROM CheckOutModel c")
    Integer findMaxId();

    // Attenzione: Fare la differenza tra una classe java e query quando si creano diverse query personalizzate
    @Query(value = "DELETE FROM public.checkout WHERE libro_id = :id", nativeQuery = true)
    @Modifying
    void deleteByLibroId(@Param("id") int id);
    
}
