package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Magasin;
import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.repository.MagasinRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/magasins")
@CrossOrigin(origins = "*")
public class MagasinController {

    @Autowired
    private MagasinRepository magasinRepository;

    @GetMapping
    public List<Magasin> getAllMagasins() {
        return magasinRepository.findAll();
    }
}
