package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
public class Utilisateur {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "nom")
    private String nom;

    @Column(name = "role")
    private String role;
}
