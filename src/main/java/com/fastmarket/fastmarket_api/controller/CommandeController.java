package com.fastmarket.fastmarket_api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastmarket.fastmarket_api.dto.AjouterAuPanierRequest;
import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.LigneCommande;
import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.CommandeRepository;
import com.fastmarket.fastmarket_api.repository.LigneCommandeRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;

@RestController
@RequestMapping("/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    //  ENDPOINT DE TEST POUR VÉRIFIER SI LE CONTROLLER EST ACTIF
    @GetMapping("/test")
    public String test() {
        return "CommandeController OK";
    }

    @PostMapping("/ajouter")
    public Commande ajouterProduitAuPanier(@RequestBody AjouterAuPanierRequest req) {
        System.out.println(">>> Ajouter produit au panier appelé");

        // 🔍 Récupération du client et produit
        Client client = clientRepository.findById(req.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));
        Produit produit = produitRepository.findById(req.getProduitId())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        // 🔍 Récupération ou création de la commande (panier) en cours
        Commande panier = commandeRepository.findByClientIdAndStatut(client.getId(), "Panier")
                .orElseGet(() -> {
            Commande nouvelleCommande = new Commande();
            nouvelleCommande.setClient(client);
            nouvelleCommande.setStatut("Panier");
            nouvelleCommande.setDateCommande(LocalDateTime.now());
            return commandeRepository.save(nouvelleCommande);
        });
        if (panier.getLignesCommande() == null) {
            panier.setLignesCommande(new ArrayList<>());
        }



        // 🔍 Vérifie si le produit est déjà dans le panier
        Optional<LigneCommande> ligneExistante = panier.getLignesCommande()
                .stream()
                .filter(l -> l.getProduit().getId().equals(produit.getId()))
                .findFirst();

        if (ligneExistante.isPresent()) {
            LigneCommande ligne = ligneExistante.get();
            ligne.setQuantite(ligne.getQuantite() + req.getQuantite());
        } else {
            LigneCommande ligne = new LigneCommande();
            ligne.setCommande(panier);
            ligne.setProduit(produit);
            ligne.setQuantite(req.getQuantite());
            panier.getLignesCommande().add(ligne);
        }

        return commandeRepository.save(panier);
    }
}
