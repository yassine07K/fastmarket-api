package com.fastmarket.fastmarket_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastmarket.fastmarket_api.model.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    Optional<Commande> findByClientIdAndStatut(Long clientId, String statut);
}
