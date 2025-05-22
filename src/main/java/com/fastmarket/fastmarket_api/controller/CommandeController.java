package com.fastmarket.fastmarket_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fastmarket.fastmarket_api.Service.CommandeService;
import com.fastmarket.fastmarket_api.model.LigneCommande;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    // Requête POST pour ajouter un produit au panier d'un client
    @PostMapping("/panier/ajouter")
    public LigneCommande ajouterProduitDansPanier(
            @RequestParam int clientId,
            @RequestParam int produitId,
            @RequestParam int quantite
    ) {
        return commandeService.ajouterProduitDansPanier(clientId, produitId, quantite);
    }
}
