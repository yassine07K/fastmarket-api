package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByCategorieId(Long id);
    List<Produit> findByMagasinId(Long id);
    @Query("SELECT p FROM Produit p " +
            "WHERE p.magasin.id = :magasinId AND (" +
            "LOWER(p.libelle) LIKE LOWER(CONCAT('%', :motcle, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :motcle, '%')) OR " +
            "LOWER(p.marque) LIKE LOWER(CONCAT('%', :motcle, '%')) OR " +
            "LOWER(p.typePromotion) LIKE LOWER(CONCAT('%', :motcle, '%')) )")
    List<Produit> rechercherProduitsParMotCleEtMagasin(@Param("motcle") String motcle, @Param("magasinId") Long magasinId);
}
