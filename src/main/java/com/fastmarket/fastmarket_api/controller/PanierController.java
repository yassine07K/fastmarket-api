package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.AjouterAuPanierRequest;
import com.fastmarket.fastmarket_api.dto.ValiderPanierRequest;
import com.fastmarket.fastmarket_api.model.*;
import com.fastmarket.fastmarket_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/panier")
@CrossOrigin(origins = "*")
public class PanierController {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private MagasinRepository magasinRepository;

    @Autowired
    private CreneauRepository creneauRepository;

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

    @PutMapping("/valider")
    public ResponseEntity<String> validerPanier(@RequestBody ValiderPanierRequest req) {
        Optional<Commande> panierOpt = commandeRepository.findByClient_IdAndStatut(req.getClientId(), "Panier");

        if (panierOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Commande panier = panierOpt.get();

        // On récupère les entités
        Magasin magasin = magasinRepository.findById(req.getMagasinId())
                .orElseThrow(() -> new RuntimeException("Magasin introuvable"));

        Creneau creneau = creneauRepository.findById(req.getCreneauId())
                .orElseThrow(() -> new RuntimeException("Créneau introuvable"));

        // Mise à jour du panier
        panier.setStatut("Commandé");
        panier.setMagasin(magasin);
        panier.setCreneau(creneau);

        commandeRepository.save(panier);

        return ResponseEntity.ok("Commande validée avec succès !");
    }


    @PostMapping("/ajouter")
    public ResponseEntity<Commande> ajouterProduitAuPanier(@RequestBody AjouterAuPanierRequest req) {

        Client client = clientRepository.findById(req.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        Produit produit = produitRepository.findById(req.getProduitId())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        Commande panier = commandeRepository.findByClient_IdAndStatut(client.getId(), "Panier")
                .orElseGet(() -> {
                    Commande nouvelleCommande = new Commande();
                    nouvelleCommande.setClient(client);
                    nouvelleCommande.setStatut("Panier");
                    nouvelleCommande.setDateCommande(LocalDateTime.now());
                    nouvelleCommande.setLignesCommande(new ArrayList<>());
                    return commandeRepository.save(nouvelleCommande);
                });

        if (panier.getLignesCommande() == null) {
            panier.setLignesCommande(new ArrayList<>());
        }

        Optional<LigneCommande> ligneExistante = panier.getLignesCommande()
                .stream()
                .filter(lc -> lc.getProduit().getId().equals(produit.getId()))
                .findFirst();

        if (ligneExistante.isPresent()) {
            ligneExistante.get().setQuantite(
                    ligneExistante.get().getQuantite() + req.getQuantite());
        } else {
            LigneCommande ligne = new LigneCommande();
            ligne.setCommande(panier);
            ligne.setProduit(produit);
            ligne.setQuantite(req.getQuantite());
            panier.getLignesCommande().add(ligne);
        }

        return ResponseEntity.ok(commandeRepository.save(panier));
    }



}
