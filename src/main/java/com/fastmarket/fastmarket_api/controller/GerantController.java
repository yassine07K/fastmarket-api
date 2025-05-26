package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.repository.GerantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/gerants")
@CrossOrigin(origins = "*") // autorise les appels depuis React ou Postman
public class GerantController {

    @Autowired
    private GerantRepository gerantRepository;

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getGerantByEmail(@PathVariable String email) {
        return gerantRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
