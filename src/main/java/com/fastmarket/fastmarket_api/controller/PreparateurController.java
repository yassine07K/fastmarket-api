package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.repository.PreparateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/preparateurs")
@CrossOrigin(origins = "*") // autorise les appels depuis React ou Postman
public class PreparateurController {

    @Autowired
    private PreparateurRepository preparateurRepository;

    // Récupérer un préparateur par son email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPreparateurByEmail(@PathVariable String email) {
        return preparateurRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
