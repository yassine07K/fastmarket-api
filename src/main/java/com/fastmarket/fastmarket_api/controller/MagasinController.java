package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Magasin;
import com.fastmarket.fastmarket_api.repository.MagasinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/magasins")
@CrossOrigin(origins = "*")
public class MagasinController {

    @Autowired
    private MagasinRepository magasinRepository;

    // Récupérer tous les magasins
    @GetMapping
    public List<Magasin> getAllMagasins() {
        return magasinRepository.findAll();
    }

    // Récupérer 5 magasins
    @GetMapping("/top5")
    public List<Magasin> getTop5Magasins() {
        return magasinRepository.findTop5ByOrderByIdAsc();
    }

    // Récupérer les magasins par id
    @GetMapping("/{id}")
    public ResponseEntity<Magasin> getMagasinById(@PathVariable Long id) {
        return magasinRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
