package com.fastmarket.fastmarket_api.repository;

import com.fastmarket.fastmarket_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmailAndMotDePasse(String email, String motDePasse);
    Optional<Client> findByEmail(String email);




}
