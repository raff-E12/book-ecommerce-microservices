package com.book.crud.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ordini", schema = "public")
public class OrdineModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "prezzo_totale", precision = 10, scale = 2)
    private BigDecimal prezzoTotale;

    @Column(nullable = false)
    private Boolean ordinato = false;

    // Relazione: un ordine contiene più righe di checkout
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CheckOutModel> righeCheckout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", referencedColumnName = "id")
    private UserModel utente;

    // ─── Costruttori ────────────────────────────────────────────────────────

    public OrdineModel() {}

    public OrdineModel(BigDecimal prezzoTotale, Boolean ordinato, UserModel utenti) {
        this.prezzoTotale = prezzoTotale;
        this.ordinato = ordinato;
        this.utente = utenti;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getPrezzoTotale() { return prezzoTotale; }
    public void setPrezzoTotale(BigDecimal prezzoTotale) { this.prezzoTotale = prezzoTotale; }

    public Boolean getOrdinato() { return ordinato; }
    public void setOrdinato(Boolean ordinato) { this.ordinato = ordinato; }

    public UserModel getUtente() { return utente; }
    public void setUtente(UserModel utente) { this.utente = utente; }

    public List<CheckOutModel> getRigheCheckout() { return righeCheckout; }
    public void setRigheCheckout(List<CheckOutModel> righeCheckout) { this.righeCheckout = righeCheckout; }
}