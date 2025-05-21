package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Produit_Rempla")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitRempla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "produit_origine_id")
    private Produit produitOrigine;

    @ManyToOne
    @JoinColumn(name = "produit_rempla_id")
    private Produit produitRempla;
}
