package com.fastmarket.fastmarket_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProduitInListe {
    private Long id;
    private String libelle;
    private String marque;
    private Double prixUnitaire;
    private Boolean enPromotion;
    private String image;
}