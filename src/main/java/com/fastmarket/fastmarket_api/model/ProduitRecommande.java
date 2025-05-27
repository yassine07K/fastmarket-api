package com.fastmarket.fastmarket_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Produit_Recommande")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduitRecommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "produit_id_recommande")
    private Produit produitRecommande;
}