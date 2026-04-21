package com.student.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordini",  schema = "public")
public class OrdersModel implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "prezzo_totale", precision = 10, scale = 2)
    private BigDecimal prezzoTotale;

    @Column(nullable = false)
    private Boolean ordinato = false;
    
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CheckOutModel> righeCheckout;

    public OrdersModel() {}

    public OrdersModel(BigDecimal prezzoTotale, Boolean ordinato) {
        this.prezzoTotale = prezzoTotale;
        this.ordinato = ordinato;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getPrezzoTotale() { return prezzoTotale; }
    public void setPrezzoTotale(BigDecimal prezzoTotale) { this.prezzoTotale = prezzoTotale; }

    public Boolean getOrdinato() { return ordinato; }
    public void setOrdinato(Boolean ordinato) { this.ordinato = ordinato; }

    public List<CheckOutModel> getRigheCheckout() { return righeCheckout; }
    public void setRigheCheckout(List<CheckOutModel> righeCheckout) { this.righeCheckout = righeCheckout; }
}
