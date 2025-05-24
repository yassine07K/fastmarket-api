package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Gerant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GerantRepository extends JpaRepository<Gerant, Long> {
    Optional<Gerant> findByNom(String nom);
    Optional<Gerant> findByEmail(String email);
}
