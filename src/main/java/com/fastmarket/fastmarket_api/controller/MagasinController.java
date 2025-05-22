package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.ProduitParCategorieDTO;
import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/magasins") // ✅ important
@CrossOrigin(origins = "*")
public class MagasinController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/{id}/produitsparcategorie")
    public Map<String, List<Produit>> getProduitsParCategorie(@PathVariable Long id) {
        List<Produit> produits = produitRepository.findByMagasinId(id);

        return produits.stream()
                .collect(Collectors.groupingBy(p -> p.getCategorie().getNom()));
    }

}
