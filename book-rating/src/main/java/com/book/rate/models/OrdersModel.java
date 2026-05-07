package com.book.rate.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ordini", schema = "public")
public class OrdersModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "prezzo_totale", precision = 10, scale = 2)
    private BigDecimal prezzoTotale;

    @Column(nullable = false)
    private Boolean ordinato = false;

    // Relazione: un ordine contiene più righe di checkout
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CheckoutModel> righeCheckout;

    // ─── Costruttori ────────────────────────────────────────────────────────

    public OrdersModel() {}

    public OrdersModel(BigDecimal prezzoTotale, Boolean ordinato) {
        this.prezzoTotale = prezzoTotale;
        this.ordinato = ordinato;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getPrezzoTotale() { return prezzoTotale; }
    public void setPrezzoTotale(BigDecimal prezzoTotale) { this.prezzoTotale = prezzoTotale; }

    public Boolean getOrdinato() { return ordinato; }
    public void setOrdinato(Boolean ordinato) { this.ordinato = ordinato; }

    public List<CheckoutModel> getRigheCheckout() { return righeCheckout; }
    public void setRigheCheckout(List<CheckoutModel> righeCheckout) { this.righeCheckout = righeCheckout; }
}