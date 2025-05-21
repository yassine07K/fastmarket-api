package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
