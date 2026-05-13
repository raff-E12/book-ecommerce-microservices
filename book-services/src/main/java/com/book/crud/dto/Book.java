package com.book.crud.dto;

import java.math.BigDecimal;

import jakarta.persistence.Column;

public record Book(
    Integer id,
    String titolo,
    String autore,
    String editore,
    Integer annoPubblicazione,
    String isbn,
    String categoria,
    Integer numCopie,
    Integer disponibile,
    String posizioneScaffale,
    String note,
    BigDecimal prezzo,
    String coverColor,
    String coverImg,
    Boolean trashed
) {}