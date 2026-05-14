package com.users.book.dto;

import java.math.BigDecimal;

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
    String coverColor
) {}