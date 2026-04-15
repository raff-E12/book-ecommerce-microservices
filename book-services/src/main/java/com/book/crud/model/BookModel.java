package com.book.crud.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

// Creazione della Entità "Book" da usare con JPA con il DB
@Entity
@Table(name = "libri")
public class BookModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String titolo;

    @Column(nullable = false, length = 100)
    private String autore;
 
    @Column(length = 100)
    private String editore;
 
    @Column(name = "anno_pubblicazione")
    private Integer annoPubblicazione;
 
    @Column(unique = true, length = 13)
    private String isbn;

    @Column(length = 50)
    private String categoria = "Narrativa";
 
    @Column(name = "num_copie")
    private Integer numCopie = 1;
 
    @Column(name = "disponibile")
    private Integer disponibile = 1;
 
    @Column(name = "posizione_scaffale", length = 20)
    private String posizioneScaffale;
 
    @Column(columnDefinition = "TEXT")
    private String note;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;

    @Column(name = "cover_color", length = 20)
    private String coverColor;
 
    // Relazione inversa: un libro può apparire in più righe di checkout
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CheckoutModel> righeCheckout;
 
    // ─── Costruttori ────────────────────────────────────────────────────────
 
    public BookModel() {}
 
    public BookModel(String titolo, String autore, String editore,
                Integer annoPubblicazione, String isbn, String categoria,
                Integer numCopie, Integer disponibile, String posizioneScaffale,
                String note, BigDecimal prezzo, String coverColor) {
        this.titolo = titolo;
        this.autore = autore;
        this.editore = editore;
        this.annoPubblicazione = annoPubblicazione;
        this.isbn = isbn;
        this.categoria = categoria;
        this.numCopie = numCopie;
        this.disponibile = disponibile;
        this.posizioneScaffale = posizioneScaffale;
        this.note = note;
        this.prezzo = prezzo;
        this.coverColor = coverColor;
    }
 
    // ─── Getters & Setters ───────────────────────────────────────────────────
 
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
 
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
 
    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }
 
    public String getEditore() { return editore; }
    public void setEditore(String editore) { this.editore = editore; }
 
    public Integer getAnnoPubblicazione() { return annoPubblicazione; }
    public void setAnnoPubblicazione(Integer annoPubblicazione) { this.annoPubblicazione = annoPubblicazione; }
 
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
 
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
 
    public Integer getNumCopie() { return numCopie; }
    public void setNumCopie(Integer numCopie) { this.numCopie = numCopie; }
 
    public Integer getDisponibile() { return disponibile; }
    public void setDisponibile(Integer disponibile) { this.disponibile = disponibile; }
 
    public String getPosizioneScaffale() { return posizioneScaffale; }
    public void setPosizioneScaffale(String posizioneScaffale) { this.posizioneScaffale = posizioneScaffale; }
 
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
 
    public BigDecimal getPrezzo() { return prezzo; }
    public void setPrezzo(BigDecimal prezzo) { this.prezzo = prezzo; }

    public String getCoverColor() { return coverColor; }
    public void setCoverColor(String coverColor) { this.coverColor = coverColor; }
 
    public List<CheckoutModel> getRigheCheckout() { return righeCheckout; }
    public void setRigheCheckout(List<CheckoutModel> righeCheckout) { this.righeCheckout = righeCheckout; }
}