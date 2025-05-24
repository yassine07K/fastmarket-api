package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.ModifierMagasinClientRequest;
import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.Magasin;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.GerantRepository;
import com.fastmarket.fastmarket_api.repository.MagasinRepository;
import com.fastmarket.fastmarket_api.repository.PreparateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getGerantByEmail(@PathVariable String email) {
        return preparateurRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
