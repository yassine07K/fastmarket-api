package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Preparateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preparateur {

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

    @ManyToOne
    @JoinColumn(name = "Magasin_id")
    private Magasin magasin;
}
