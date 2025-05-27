package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.service.ImportProduitService;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/import")
@CrossOrigin(origins = "*")
public class ImportController {

    @Autowired
    private ImportProduitService importProduitService;

    @PostMapping("/produits")
    public ResponseEntity<?> importerProduits(@RequestParam("file") MultipartFile file) {
        try {
            List<CSVRecord> records = importProduitService.lireProduitsDepuisCsv(file);
            return ResponseEntity.ok("Nombre de lignes lues : " + records.size());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}