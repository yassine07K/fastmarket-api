package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produits")
@CrossOrigin(origins = "*")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    // Récupérer tous les produits
    @GetMapping
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    // Produits d'une catégorie
    @GetMapping("/categorie/{id}")
    public List<Produit> getByCategorie(@PathVariable Long id) {
        return produitRepository.findByCategorieId(id);
    }

    // Produits d'un magasin
    @GetMapping("/magasin/{id}")
    public List<Produit> getByMagasin(@PathVariable Long id) {
        return produitRepository.findByMagasinId(id);
    }

    // Créer un produit
    @PostMapping
    public Produit createProduit(@RequestBody Produit produit) {
        return produitRepository.save(produit);
    }

    // Mettre à jour un produit
    @PutMapping("/{id}")
    public Produit updateProduit(@PathVariable Long id, @RequestBody Produit updatedProduit) {
        return produitRepository.findById(id).map(p -> {
            p.setLibelle(updatedProduit.getLibelle());
            p.setPrixUnitaire(updatedProduit.getPrixUnitaire());
            p.setPrixKg(updatedProduit.getPrixKg());
            p.setNutriscore(updatedProduit.getNutriscore());
            p.setPoids(updatedProduit.getPoids());
            p.setEnPromotion(updatedProduit.getEnPromotion());
            p.setTypePromotion(updatedProduit.getTypePromotion());
            p.setImage(updatedProduit.getImage());
            p.setMarque(updatedProduit.getMarque());
            p.setDescription(updatedProduit.getDescription());
            p.setCategorie(updatedProduit.getCategorie());
            p.setMagasin(updatedProduit.getMagasin());
            return produitRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }

    // Supprimer un produit
    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitRepository.deleteById(id);
    }
    //Récupérer un produit
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        return produitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<Produit>> rechercherProduits(
            @RequestParam String motcle,
            @RequestParam Long magasinId) {

        List<Produit> resultats = produitRepository.rechercherProduitsParMotCleEtMagasin(motcle, magasinId);
        return ResponseEntity.ok(resultats);
    }

    @GetMapping("/gerant/{gerantId}")
    public ResponseEntity<List<Produit>> getProduitsParGerant(@PathVariable Long gerantId) {
        List<Produit> produits = produitRepository.findByMagasin_Gerant_Id(gerantId);
        return ResponseEntity.ok(produits);
    }


}
