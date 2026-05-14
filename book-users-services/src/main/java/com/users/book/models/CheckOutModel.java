package com.users.book.models;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "checkout",
    uniqueConstraints = {
        // Rispecchia il UNIQUE (ordine_id, libro_id) del DB:
        // un libro può comparire una sola volta per ordine
        @UniqueConstraint(
            name = "uq_checkout_ordine_libro",
            columnNames = { "ordine_id", "libro_id" }
        )
    },
    schema = "public"
)
public class CheckOutModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // FK → ordini.id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ordine_id", nullable = false)
    private OrdersModel ordine;

    // FK → libri.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    private BookModel libro;

    @Column(name = "libro_prezzo", nullable = false, precision = 10, scale = 2)
    private BigDecimal libroPrezzo;

    @Column(nullable = false)
    private Integer quantita = 1;

    @Column(name = "prezzo_subtotale", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoSubtotale;

    // ─── Costruttori ────────────────────────────────────────────────────────

    public CheckOutModel() {}

    public CheckOutModel(OrdersModel ordine, BookModel libro, BigDecimal libroPrezzo, Integer quantita) {
        this.ordine = ordine;
        this.libro = libro;
        this.libroPrezzo = libroPrezzo;
        this.quantita = quantita;
        // Il subtotale viene calcolato automaticamente
        this.prezzoSubtotale = libroPrezzo.multiply(BigDecimal.valueOf(quantita));
    }

    // ─── Metodo di utilità: ricalcola il subtotale ───────────────────────────

    @PrePersist
    @PreUpdate
    public void calcolaSubtotale() {
        if (libroPrezzo != null && quantita != null) {
            this.prezzoSubtotale = libroPrezzo.multiply(BigDecimal.valueOf(quantita));
        }
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public OrdersModel getOrdine() { return ordine; }
    public void setOrdine(OrdersModel ordine) { this.ordine = ordine; }

    public BookModel getLibro() { return libro; }
    public void setLibro(BookModel libro) { this.libro = libro; }

    public BigDecimal getLibroPrezzo() { return libroPrezzo; }
    public void setLibroPrezzo(BigDecimal libroPrezzo) {
        this.libroPrezzo = libroPrezzo;
        calcolaSubtotale();
    }

    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
        calcolaSubtotale();
    }

    public BigDecimal getPrezzoSubtotale() { return prezzoSubtotale; }
    public void setPrezzoSubtotale(BigDecimal prezzoSubtotale) { this.prezzoSubtotale = prezzoSubtotale; }
}

