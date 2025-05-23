package com.fastmarket.fastmarket_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastmarket.fastmarket_api.model.LigneCommande;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Integer> {
}
