package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByCategorieId(Long categorieId);
    List<Produit> findByMagasinId(Long magasinId);


}
