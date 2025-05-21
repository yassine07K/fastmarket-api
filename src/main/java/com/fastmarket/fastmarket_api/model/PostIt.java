package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Post_it")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Produit d'origine (celui qui devait être pris)
    @ManyToOne
    @JoinColumn(name = "produit_origine_id")
    private Produit produitOrigine;

    // Produit de remplacement proposé
    @ManyToOne
    @JoinColumn(name = "produit_rempla_id")
    private Produit produitRempla;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "Liste_Courses_id")
    private ListeCourses listeCourses;
}
