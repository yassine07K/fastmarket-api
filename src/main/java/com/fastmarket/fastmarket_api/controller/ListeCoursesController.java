package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.*;
import com.fastmarket.fastmarket_api.model.*;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.ListeCoursesRepository;
import com.fastmarket.fastmarket_api.repository.PostItRepository;
import com.fastmarket.fastmarket_api.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listesCourses")
@CrossOrigin(origins = "*")
public class ListeCoursesController {

    @Autowired
    private ListeCoursesRepository listeCoursesRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private PostItRepository postItRepository;

    // Créer une liste de courses
    @PostMapping("/creer")
    public ResponseEntity<ListeCourses> creerNouvelleListe(@RequestBody CreerListeCoursesRequest req) {
        Client client = clientRepository.findById(req.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        ListeCourses nouvelleListe = new ListeCourses();
        nouvelleListe.setNom(req.getNom());
        nouvelleListe.setClient(client);

        ListeCourses saved = listeCoursesRepository.save(nouvelleListe);
        return ResponseEntity.ok(saved);
    }

    //Voir toutes les listes de courses d’un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ListeCoursesResume>> getListesDuClient(@PathVariable Long clientId) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);

        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Client client = clientOpt.get();

        List<ListeCoursesResume> listes = client.getListesCourses().stream()
                .map(l -> new ListeCoursesResume(l.getId(), l.getNom()))
                .toList();

        return ResponseEntity.ok(listes);
    }


    // Voir une liste de courses par ID
    @GetMapping("/{listeId}/details")
    public ResponseEntity<ListeCoursesDetails> getDetailsListeCourses(@PathVariable Long listeId) {
        Optional<ListeCourses> listeOpt = listeCoursesRepository.findById(listeId);

        if (listeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ListeCourses liste = listeOpt.get();

        List<ProduitInListe> produits = liste.getProduits().stream()
                .map(lp -> new ProduitInListe(
                        lp.getProduit().getId(),
                        lp.getProduit().getLibelle(),
                        lp.getProduit().getMarque(),
                        lp.getProduit().getPrixUnitaire(),
                        lp.getProduit().getEnPromotion(),
                        lp.getProduit().getImage()
                ))
                .toList();

        List<PostIt> postIts = liste.getPostIts(); // s'il est bien mappé

        ListeCoursesDetails dto = new ListeCoursesDetails(
                liste.getId(),
                liste.getNom(),
                produits,
                postIts
        );

        return ResponseEntity.ok(dto);
    }


    //Modifier le nom d’une liste
    @PutMapping("/{listeId}")
    public ResponseEntity<ListeCourses> modifierNomListe(
            @PathVariable Long listeId,
            @RequestBody ModifierNomListeRequest req) {

        Optional<ListeCourses> optListe = listeCoursesRepository.findById(listeId);
        if (optListe.isEmpty()) return ResponseEntity.notFound().build();

        ListeCourses liste = optListe.get();
        liste.setNom(req.getNom());
        return ResponseEntity.ok(listeCoursesRepository.save(liste));
    }

    // Supprimer une liste de courses
    @DeleteMapping("/{listeId}")
    public ResponseEntity<?> supprimerListe(@PathVariable Long listeId) {
        if (!listeCoursesRepository.existsById(listeId)) {
            return ResponseEntity.notFound().build();
        }
        listeCoursesRepository.deleteById(listeId);
        return ResponseEntity.ok().build();
    }

    // Ajouter un produit à une liste de courses
    @PostMapping("/produits/ajouter")
    public ResponseEntity<?> ajouterProduitAListe(@RequestBody AjouterProduitListeRequest req) {
        ListeCourses liste = listeCoursesRepository.findById(req.getListeId())
                .orElseThrow(() -> new RuntimeException("Liste introuvable"));

        Produit produit = produitRepository.findById(req.getProduitId())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        ListeCoursesProduit lien = new ListeCoursesProduit();
        lien.setListe(liste);
        lien.setProduit(produit);

        liste.getProduits().add(lien);
        listeCoursesRepository.save(liste);

        return ResponseEntity.ok().build();
    }

    // Ajouter un Post-It à une liste de courses
    @PostMapping("/postits/ajouter")
    public ResponseEntity<?> ajouterPostItAListe(@RequestBody AjouterPostItListeRequest req) {
        ListeCourses liste = listeCoursesRepository.findById(req.getListeId())
                .orElseThrow(() -> new RuntimeException("Liste introuvable"));

        PostIt postIt = new PostIt();
        postIt.setContenu(req.getContenu());

        postIt = postItRepository.save(postIt);

        liste.getPostIts().add(postIt);
        listeCoursesRepository.save(liste);

        return ResponseEntity.ok().build();
    }
}