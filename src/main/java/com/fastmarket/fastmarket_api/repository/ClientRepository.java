package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.Gerant;
import com.fastmarket.fastmarket_api.model.Preparateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmailAndMotDePasse(String email, String motDePasse);
    Optional<Client> findByNom(String nom);
    Optional<Client> findByEmail(String email);




}
