package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Utilisateur;
import com.fastmarket.fastmarket_api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur request) {
        Optional<Utilisateur> optUser = utilisateurRepository.findByEmailAndMotDePasse(
                request.getEmail(),
                request.getMotDePasse()
        );

        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        return ResponseEntity.ok(optUser.get()); // Renvoie tout l'objet utilisateur
    }
}
