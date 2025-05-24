package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Preparateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreparateurRepository extends JpaRepository<Preparateur, Long> {
    Optional<Preparateur> findByNom(String nom);
    Optional<Preparateur> findByEmail(String email);
}
