package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Magasin")
public class Magasin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "adresse")
    private String adresse;
}

