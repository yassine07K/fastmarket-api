package com.fastmarket.fastmarket_api.repository;


import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    // ✅ Panier actif d’un client (statut = "Panier")
    Optional<Commande> findByClient_IdAndStatut(Long clientId, String statut);

    // ✅ Toutes les commandes passées d’un client (hors panier)
    List<Commande> findByClient_IdAndStatutNot(Long clientId, String statut);

    // ✅ Tous les paniers existants (utile pour admin ou tests)
    List<Commande> findByStatut(String statut);

    // ✅ Toutes les commandes d’un client, quel que soit le statut
    List<Commande> findByClient_Id(Long clientId);

    int countByCreneau_Id(Long creneauId);

    List<Commande> findByMagasin_IdAndPreparateur_IdAndStatutIn(Long magasinId, Long preparateurId, List<String> statuts);
}
