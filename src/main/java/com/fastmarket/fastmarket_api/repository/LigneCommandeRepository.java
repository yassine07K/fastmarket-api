package com.fastmarket.fastmarket_api.repository;


import com.fastmarket.fastmarket_api.model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

    // 🔍 Lignes d'une commande (ex : pour afficher les produits dans un panier)
    List<LigneCommande> findByCommande_Id(Long commandeId);

    // 🔍 Lignes contenant un produit précis
    List<LigneCommande> findByProduit_Id(Long produitId);

    // 🔍 Ligne spécifique pour un produit donné dans une commande
    LigneCommande findByCommande_IdAndProduit_Id(Long commandeId, Long produitId);

    // ❌ Supprimer toutes les lignes d'une commande
    void deleteByCommande_Id(Long commandeId);

}
