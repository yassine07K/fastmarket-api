package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.CreerListeCoursesRequest;
import com.fastmarket.fastmarket_api.dto.ModifierNomListeRequest;
import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.ListeCourses;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.ListeCoursesRepository;
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

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ListeCourses>> getListesParClient(@PathVariable Long clientId) {
        List<ListeCourses> listes = listeCoursesRepository.findByClient_Id(clientId);
        return ResponseEntity.ok(listes);
    }

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

    @DeleteMapping("/{listeId}")
    public ResponseEntity<?> supprimerListe(@PathVariable Long listeId) {
        if (!listeCoursesRepository.existsById(listeId)) {
            return ResponseEntity.notFound().build();
        }
        listeCoursesRepository.deleteById(listeId);
        return ResponseEntity.ok().build();
    }
}