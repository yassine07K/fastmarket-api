package com.fastmarket.fastmarket_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Gerant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gerant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "adresse")
    private String adresse;

    @OneToMany(mappedBy = "gerant")
    @JsonIgnore // ← si tu veux éviter les cycles JSON dans les réponses API
    private List<Magasin> magasins;

}
