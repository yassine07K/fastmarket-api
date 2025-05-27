package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Categorie;
import com.fastmarket.fastmarket_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByNomIgnoreCase(String categorieNom);
}
