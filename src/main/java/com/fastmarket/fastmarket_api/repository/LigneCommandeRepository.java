package com.fastmarket.fastmarket_api.repository;


import com.fastmarket.fastmarket_api.model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

    List<LigneCommande> findByCommande_Id(Long commandeId);
    List<LigneCommande> findByProduit_Id(Long produitId);
    LigneCommande findByCommande_IdAndProduit_Id(Long commandeId, Long produitId);
    void deleteByCommande_Id(Long commandeId);

}
