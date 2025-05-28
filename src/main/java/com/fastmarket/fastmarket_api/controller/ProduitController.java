package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.ProduitInListe;
import com.fastmarket.fastmarket_api.model.Categorie;
import com.fastmarket.fastmarket_api.model.Magasin;
import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.model.ProduitRecommande;
import com.fastmarket.fastmarket_api.repository.CategorieRepository;
import com.fastmarket.fastmarket_api.repository.MagasinRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRecommandeRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/produits")
@CrossOrigin(origins = "*")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitRecommandeRepository produitRecommandeRepository;

    @Autowired
    private MagasinRepository magasinRepository;

    @Autowired
    private CategorieRepository categorieRepository;

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

    @GetMapping("/{produitId}/recommandes")
    public ResponseEntity<List<ProduitInListe>> getProduitsRecommandes(@PathVariable Long produitId) {
        List<ProduitRecommande> relations = produitRecommandeRepository.findByProduit_Id(produitId);

        List<ProduitInListe> produitsDto = relations.stream()
                .map(rel -> {
                    Produit p = rel.getProduitRecommande();
                    return new ProduitInListe(
                            p.getId(),
                            p.getLibelle(),
                            p.getMarque(),
                            p.getPrixUnitaire(),
                            p.getEnPromotion(),
                            p.getImage()
                    );
                })
                .toList();

        return ResponseEntity.ok(produitsDto);
    }

    @PostMapping("/importer/{gerantId}")
    public ResponseEntity<String> importerProduits(@RequestParam("file") MultipartFile file, @PathVariable Long gerantId) {
        try {
            // Étape 1 : Récupération du magasin du gérant
            Magasin magasin = (Magasin) magasinRepository.findByGerant_Id(gerantId)
                    .orElseThrow(() -> new RuntimeException("Magasin non trouvé pour ce gérant"));

            // Étape 2 : Lecture du fichier
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            List<CSVRecord> records = parser.getRecords();

            for (CSVRecord record : records) {
                String libelle = record.get("libelle");

                // Vérifie si le produit existe déjà dans ce magasin
                boolean exists = produitRepository.findByLibelleAndMagasin_Id(libelle, magasin.getId()).isPresent();
                if (exists) continue;

                // Création du produit
                Produit produit = new Produit();
                produit.setLibelle(libelle);
                produit.setPrixUnitaire(Double.parseDouble(record.get("prix_unitaire")));
                produit.setPrixKg(Double.parseDouble(record.get("prix_kg")));
                produit.setNutriscore(record.get("nutriscore"));
                produit.setPoids(Double.parseDouble(record.get("poids")));
                produit.setEnPromotion(Boolean.parseBoolean(record.get("en_promotion")));
                produit.setTypePromotion(record.get("type_promotion"));
                produit.setImage(record.get("image"));
                produit.setMarque(record.get("marque"));
                produit.setDescription(record.get("description"));
                produit.setMagasin(magasin);

                // Gestion de la catégorie
                String categorieNom = record.get("categorie");
                Categorie categorie = categorieRepository.findByNomIgnoreCase(categorieNom)
                        .orElseGet(() -> {
                            Categorie nouvelle = new Categorie();
                            nouvelle.setNom(categorieNom);
                            return categorieRepository.save(nouvelle);
                        });
                produit.setCategorie(categorie);

                // Sauvegarde du produit
                produitRepository.save(produit);
            }

            return ResponseEntity.ok("Produits importés avec succès (" + records.size() + " lignes)");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'import : " + e.getMessage());
        }
    }


}
