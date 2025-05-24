package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.ModifierMagasinClientRequest;
import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.Commande;
import com.fastmarket.fastmarket_api.model.Magasin;
import com.fastmarket.fastmarket_api.model.Preparateur;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.CommandeRepository;
import com.fastmarket.fastmarket_api.repository.MagasinRepository;
import com.fastmarket.fastmarket_api.repository.PreparateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {

    @Autowired
    private PreparateurRepository preparateurRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping("/preparateur/{preparateurId}/commandes/commandees")
    public ResponseEntity<List<Commande>> getCommandesCommandeesParMagasin(@PathVariable Long preparateurId) {
        Preparateur preparateur = preparateurRepository.findById(preparateurId)
                .orElseThrow(() -> new RuntimeException("Préparateur introuvable"));

        List<Commande> commandes = commandeRepository.findByMagasinIdAndStatut(
                preparateur.getMagasin().getId(), "Commandé");

        return ResponseEntity.ok(commandes);
    }




}
