package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.CreerListeCoursesRequest;
import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.ListeCourses;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.ListeCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}