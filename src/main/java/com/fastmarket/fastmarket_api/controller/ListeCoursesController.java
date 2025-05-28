package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.*;
import com.fastmarket.fastmarket_api.model.*;
import com.fastmarket.fastmarket_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ListeCoursesPostItRepository listeCoursesPostItRepository;

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


    // Récupérer une liste de courses par ID
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
    @PostMapping("/{listeId}/produits")
    public ResponseEntity<?> ajouterProduitAListe(
            @PathVariable Long listeId,
            @RequestBody AjouterProduitListeRequest req) {

        ListeCourses liste = listeCoursesRepository.findById(listeId)
                .orElseThrow(() -> new RuntimeException("Liste introuvable"));

        Produit produit = produitRepository.findById(req.getProduitId())
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        // Vérifie si le produit est déjà dans la liste
        boolean existeDeja = liste.getProduits().stream()
                .anyMatch(lien -> lien.getProduit().getId().equals(produit.getId()));

        if (existeDeja) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Le produit est déjà présent dans la liste.");
        }

        // Ajout du produit
        ListeCoursesProduit lien = new ListeCoursesProduit();
        lien.setListe(liste);
        lien.setProduit(produit);

        liste.getProduits().add(lien);
        listeCoursesRepository.save(liste);

        return ResponseEntity.ok().build();
    }

    // Ajouter un Post-It à une liste de courses
    @PostMapping("/{listeId}/postits")
    public ResponseEntity<?> ajouterPostItAListe(@PathVariable Long listeId, @RequestBody AjouterPostItListeRequest req) {
        ListeCourses liste = listeCoursesRepository.findById(listeId)
                .orElseThrow(() -> new RuntimeException("Liste introuvable"));

        PostIt postIt = new PostIt();
        postIt.setContenu(req.getContenu());

        postIt = postItRepository.save(postIt);

        liste.getPostIts().add(postIt);
        listeCoursesRepository.save(liste);

        return ResponseEntity.ok().build();
    }

    // Supprimer un Post-It d'une liste de courses
    @DeleteMapping("/{listeId}/postits/{postItId}")
    public ResponseEntity<?> supprimerPostItDeListe(@PathVariable Long listeId, @PathVariable Long postItId) {
        ListeCourses liste = listeCoursesRepository.findById(listeId)
                .orElseThrow(() -> new RuntimeException("Liste introuvable"));

        PostIt postIt = postItRepository.findById(postItId)
                .orElseThrow(() -> new RuntimeException("Post-it introuvable"));

        liste.getPostIts().removeIf(p -> p.getId().equals(postItId));
        listeCoursesRepository.save(liste);

        postItRepository.delete(postIt);

        return ResponseEntity.ok().build();
    }

    // Supprimer un produit d'une liste de courses
    @DeleteMapping("/{listeId}/produits/{produitId}")
    public ResponseEntity<String> supprimerProduitDeListe(@PathVariable Long listeId, @PathVariable Long produitId) {
        Optional<ListeCourses> listeOpt = listeCoursesRepository.findById(listeId);

        if (listeOpt.isEmpty()) return ResponseEntity.notFound().build();

        ListeCourses liste = listeOpt.get();

        boolean removed = liste.getProduits().removeIf(lp -> lp.getProduit().getId().equals(produitId));

        if (!removed) return ResponseEntity.status(404).body("Produit non trouvé dans la liste");

        listeCoursesRepository.save(liste);
        return ResponseEntity.ok("Produit supprimé de la liste");
    }

    // Modifier le contenu d'un Post-It dans une liste de courses
    @PutMapping("/{listeId}/postits/{postItId}")
    public ResponseEntity<?> modifierPostItDeListe(
            @PathVariable Long listeId,
            @PathVariable Long postItId,
            @RequestBody ModifierPostItRequest req) {

        ListeCourses liste = listeCoursesRepository.findById(listeId)
                .orElseThrow(() -> new RuntimeException("Liste de courses introuvable"));

        // Vérifie que le Post-It appartient à la liste
        PostIt postIt = postItRepository.findById(postItId)
                .orElseThrow(() -> new RuntimeException("Post-It introuvable"));

        if (!liste.getPostIts().contains(postIt)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ce post-it n'appartient pas à cette liste");
        }

        // Modification du contenu
        postIt.setContenu(req.getContenu());
        postItRepository.save(postIt);

        return ResponseEntity.ok("Post-It modifié avec succès");
    }
}