package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.ProduitParCategorieDTO;
import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produits")
@CrossOrigin(origins = "*") // autorise les appels venant de React
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<Produit> getAll() {
        return produitRepository.findAll();
    }

    @PostMapping
    public Produit create(@RequestBody Produit produit) {
        return produitRepository.save(produit);
    }

    @GetMapping("/categorie/{id}")
    public List<Produit> getProduitsByCategorie(@PathVariable Long id) {
        return produitRepository.findByCategorieId(id);
    }


}
