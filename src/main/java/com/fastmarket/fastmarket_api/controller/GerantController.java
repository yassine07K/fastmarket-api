package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Gerant;
import com.fastmarket.fastmarket_api.model.Magasin;
import com.fastmarket.fastmarket_api.repository.GerantRepository;
import com.fastmarket.fastmarket_api.repository.MagasinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/gerants")
@CrossOrigin(origins = "*") // autorise les appels depuis React ou Postman
public class GerantController {

    @Autowired
    private GerantRepository gerantRepository;

    @Autowired
    private MagasinRepository magasinRepository;

    // Récupérer un gérant par son mail
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getGerantByEmail(@PathVariable String email) {
        return gerantRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Récupérer le magasin d'un gérant par l'ID du gérant
    @GetMapping("/{gerantId}/magasin")
    public ResponseEntity<Magasin> getMagasinByGerant(@PathVariable Long gerantId) {
        Optional<Gerant> gerantOpt = gerantRepository.findById(gerantId);
        if (gerantOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Magasin> magasinOpt = magasinRepository.findByGerant_Id(gerantId);
        return magasinOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
