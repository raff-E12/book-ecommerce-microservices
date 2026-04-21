package com.student.orders.global.interfaces;

import java.math.BigDecimal;

public class BookQuery {
    private Integer id;
    private String titolo;
    private String autore;
    private String editore;
    private Integer annoPubblicazione;
    private String isb;
    private String categoria;
    private Integer numCopie;
    private Integer disponibile;
    private String posizioneScaffale;
    private String note;
    private BigDecimal prezzo;
    private String coverColor;

    // Getter
    public Integer getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getEditore() {
        return editore;
    }

    public Integer getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public String getIsb() {
        return isb;
    }

    public String getCategoria() {
        return categoria;
    }

    public Integer getNumCopie() {
        return numCopie;
    }

    public Integer getDisponibile() {
        return disponibile;
    }

    public String getPosizioneScaffale() {
        return posizioneScaffale;
    }

    public String getNote() {
        return note;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public String getCoverColor() {
        return coverColor;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public void setAnnoPubblicazione(Integer annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public void setIsb(String isb) {
        this.isb = isb;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setNumCopie(Integer numCopie) {
        this.numCopie = numCopie;
    }

    public void setDisponibile(Integer disponibile) {
        this.disponibile = disponibile;
    }

    public void setPosizioneScaffale(String posizioneScaffale) {
        this.posizioneScaffale = posizioneScaffale;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public void setCoverColor(String coverColor) {
        this.coverColor = coverColor;
    }
}