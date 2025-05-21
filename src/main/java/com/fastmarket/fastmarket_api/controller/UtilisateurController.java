package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Utilisateur;
import com.fastmarket.fastmarket_api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "*") // pour autoriser les appels frontend
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // GET /utilisateurs
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // POST /utilisateurs/login
    @PostMapping("/login")
    public ResponseEntity<Utilisateur> login(@RequestBody Utilisateur user) {
        return utilisateurRepository.findByEmailAndMotDePasse(
                        user.getEmail(),
                        user.getMotDePasse()
                ).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
