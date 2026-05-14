package com.users.book.models;

import java.io.Serializable;

import org.hibernate.annotations.ManyToAny;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="recensioni", schema = "public")
public class RateModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    private BookModel libro;

    @Column(name="descrizione", length = 500)
    private String descrizione;

    @Column(name = "voto", nullable = false)
    Integer voto;

    @Column(name = "\"check\"", nullable = false) // Distinzione Keyword SQL
    boolean checked;

    public RateModel() {}

    public RateModel(
        BookModel libro,
        String descrizione,
        Integer voto,
        boolean checked
    ) {
        this.libro = libro;
        this.descrizione = descrizione;
        this.voto = voto;
        this.checked = checked;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BookModel getLibro() { return libro; }
    public void setLibro(BookModel libro) { this.libro = libro; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Integer getVoto() { return voto; }
    public void setVoto(Integer voto) { this.voto = voto; }

    public boolean isChecked() { return checked; }
    public void setChecked(boolean checked) { this.checked = checked; }

}
