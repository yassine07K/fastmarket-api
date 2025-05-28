package com.fastmarket.fastmarket_api.repository;


import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    Optional<Commande> findByClient_IdAndStatut(Long clientId, String statut);
    List<Commande> findByClient_IdAndStatutNot(Long clientId, String statut);
    List<Commande> findByStatut(String statut);
    List<Commande> findByClient_Id(Long clientId);
    int countByCreneau_Id(Long creneauId);
    List<Commande> findByMagasin_IdAndPreparateur_IdAndStatutIn(Long magasinId, Long preparateurId, List<String> statuts);
    List<Commande> findByMagasin_IdAndStatutIn(Long magasinId, List<String> statuts);
}
