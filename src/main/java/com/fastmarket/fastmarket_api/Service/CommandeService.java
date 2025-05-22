package com.fastmarket.fastmarket_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.LigneCommande;
import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.CommandeRepository;
import com.fastmarket.fastmarket_api.repository.Ligne_CommandeRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private Ligne_CommandeRepository ligneCommandeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ClientRepository clientRepository;

    public LigneCommande ajouterProduitDansPanier(int clientId, int produitId, int quantite) {
        // 1. Vérifier si le client existe
        Client client = clientRepository.findById((long) clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));

        // 2. Récupérer ou créer une commande avec statut "Panier" pour ce client
        Commande commande = commandeRepository
                .findByClientIdAndStatut((long) clientId, "Panier")
                .orElseGet(() -> {
                    Commande nouvelleCommande = new Commande();
                    nouvelleCommande.setClient(client);
                    nouvelleCommande.setStatut("Panier");
                    return commandeRepository.save(nouvelleCommande);
                });

        // 3. Vérifier que le produit existe
        Produit produit = produitRepository.findById((long) produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé"));

        // 4. Créer une ligne de commande
        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setCommande(commande);
        ligneCommande.setProduit(produit);
        ligneCommande.setQuantite(quantite);

        return ligneCommandeRepository.save(ligneCommande);
    }
}
