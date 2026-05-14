package com.users.book.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "utenti", schema = "public")
public class UserModel implements Serializable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Column(name = "nome_completo", length = 256)
    private String nomeCompleto;
 
    @Column(name = "email", length = 273, unique = true)
    private String email;
 
    @Column(name = "password", length = 264)
    private String password;
 
    @Column(name = "role", length = 255)
    private String role;
 
    @Column(name = "verified")
    private Boolean verified = false;

    @JsonIgnore
    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrdersModel> ordini;
 
    // Costruttori
    public UserModel() {}
 
    public UserModel(String nomeCompleto, String email, String password, String role, Boolean verified) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.password = password;
        this.role = role;
        this.verified = verified;
    }
 
    // Getter e Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
 
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
 
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
 
    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }
 
    public List<OrdersModel> getOrdini() { return ordini; }
    public void setOrdini(List<OrdersModel> ordini) { this.ordini = ordini; }
 
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", verified=" + verified +
                '}';
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) &&
                Objects.equals(email, userModel.email);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
