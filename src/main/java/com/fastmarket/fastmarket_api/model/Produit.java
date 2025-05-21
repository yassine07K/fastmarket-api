package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Produit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "prix_kg")
    private Double prixKg;

    @Column(name = "nutriscore")
    private String nutriscore;

    @Column(name = "poids")
    private Double poids;

    @Column(name = "en_promotion")
    private Boolean enPromotion;

    @Column(name = "type_promotion")
    private String typePromotion;

    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "marque")
    private String marque;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "magasin_id")
    private Magasin magasin;

    @ManyToMany(mappedBy = "produits")
    private List<ListeCourses> listesCourses;


}
