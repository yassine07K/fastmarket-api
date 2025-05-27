package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.ProduitRecommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRecommandeRepository extends JpaRepository<ProduitRecommande, Long> {
    List<ProduitRecommande> findByProduit_Id(Long produitId);
}