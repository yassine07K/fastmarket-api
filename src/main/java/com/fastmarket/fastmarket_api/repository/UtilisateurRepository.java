package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
    Optional<Utilisateur> findByEmailAndMotDePasse(String email, String motDePasse);
}
