package com.fastmarket.fastmarket_api.service;

import com.fastmarket.fastmarket_api.model.Categorie;
import com.fastmarket.fastmarket_api.model.Produit;
import com.fastmarket.fastmarket_api.repository.CategorieRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class ImportProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    public void importProduits(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }

                String[] fields = line.split(";");
                if (fields.length < 11) continue;

                Produit produit = new Produit();
                produit.setLibelle(fields[0]);
                produit.setPrixUnitaire(parseDouble(fields[1]));
                produit.setPrixKg(parseDouble(fields[2]));
                produit.setNutriscore(fields[3]);
                produit.setPoids(parseDouble(fields[4]));
                produit.setEnPromotion(Boolean.parseBoolean(fields[5]));
                produit.setTypePromotion(fields[6]);
                produit.setImage(fields[7]);
                produit.setMarque(fields[8]);
                produit.setDescription(fields[9]);

                String categorieNom = fields[10];
                Categorie categorie = categorieRepository.findByNomIgnoreCase(categorieNom)
                        .orElseGet(() -> {
                            Categorie newCat = new Categorie();
                            newCat.setNom(categorieNom);
                            return categorieRepository.save(newCat);
                        });

                produit.setCategorie(categorie);
                produitRepository.save(produit);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'import du fichier : " + e.getMessage());
        }
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (Exception e) {
            return null;
        }
    }
}