package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.LigneCommande;
import com.fastmarket.fastmarket_api.repository.CommandeRepository;
import com.fastmarket.fastmarket_api.repository.LigneCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/panier")
@CrossOrigin(origins = "*")
public class PanierController {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    /**
     * Visualiser le panier actif d’un client
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<Commande> getPanierClient(@PathVariable Long clientId) {
        Optional<Commande> panier = commandeRepository.findByClient_IdAndStatut(clientId, "Panier");
        return panier.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprimer une ligne du panier (par ID ligneCommande)
     */
    @DeleteMapping("/ligne/{ligneId}")
    public ResponseEntity<?> supprimerLigneDuPanier(@PathVariable Long ligneId) {
        Optional<LigneCommande> ligne = ligneCommandeRepository.findById(ligneId);
        if (ligne.isPresent()) {
            ligneCommandeRepository.deleteById(ligneId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Vider entièrement le panier d’un client
     */
    @DeleteMapping("/{clientId}/vider")
    public ResponseEntity<?> viderPanier(@PathVariable Long clientId) {
        Optional<Commande> panier = commandeRepository.findByClient_IdAndStatut(clientId, "Panier");
        if (panier.isPresent()) {
            ligneCommandeRepository.deleteAll(panier.get().getLignesCommande());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Valider le panier actif d'un client
     */
    @PutMapping("/valider/{clientId}")
    public ResponseEntity<?> validerPanier(@PathVariable Long clientId) {
        return commandeRepository.findByClient_IdAndStatut(clientId, "Panier")
                .map(panier -> {
                    panier.setStatut("Commandé");
                    panier.setDateCommande(LocalDateTime.now());
                    commandeRepository.save(panier);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.status(404).build());
    }


}
