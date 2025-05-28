package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.StatutCommandeRequest;
import com.fastmarket.fastmarket_api.dto.TraiterCommandeRequest;
import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.Preparateur;
import com.fastmarket.fastmarket_api.repository.CommandeRepository;
import com.fastmarket.fastmarket_api.repository.PreparateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {

    @Autowired
    private PreparateurRepository preparateurRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    // Récupérer toutes les commandes d'un préparateur
    @GetMapping("/magasin/{magasinId}/commandes/commandees")
    public ResponseEntity<List<Commande>> getCommandesFiltreesParMagasin(@PathVariable Long magasinId) {
        List<String> statuts = List.of("Commandé", "En cours de traitement", "Traité");

        List<Commande> commandes = commandeRepository.findByMagasin_IdAndStatutIn(magasinId, statuts);

        return ResponseEntity.ok(commandes);
    }

    // Passer une commande au statut "En cours de traitement"
    @PutMapping("/traiter")
    public ResponseEntity<String> traiterCommande(@RequestBody TraiterCommandeRequest req) {
        Commande commande = commandeRepository.findById(req.getCommandeId())
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (!commande.getStatut().equalsIgnoreCase("Commandé")) {
            return ResponseEntity.badRequest().body("La commande n'est pas au statut 'Commandé'.");
        }

        Preparateur preparateur = preparateurRepository.findById(req.getPreparateurId())
                .orElseThrow(() -> new RuntimeException("Préparateur introuvable"));

        commande.setStatut("En cours de traitement");
        commande.setPreparateur(preparateur);
        commandeRepository.save(commande);

        return ResponseEntity.ok("Commande marquée comme 'En cours de traitement'.");
    }

    // Marquer une commande comme "Traité"
    @PutMapping("/marquerTraitee")
    public ResponseEntity<String> marquerCommandeCommeTraitee(@RequestBody StatutCommandeRequest req) {
        Commande commande = commandeRepository.findById(req.getCommandeId())
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if (!commande.getStatut().equalsIgnoreCase("En cours de traitement")) {
            return ResponseEntity.badRequest().body("La commande n'est pas en cours de traitement.");
        }

        commande.setStatut("Traité");
        commandeRepository.save(commande);

        return ResponseEntity.ok("Commande marquée comme 'Traité'.");
    }




}
