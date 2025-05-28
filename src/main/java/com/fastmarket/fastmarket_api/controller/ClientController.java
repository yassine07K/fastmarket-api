package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.dto.ModifierMagasinClientRequest;
import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.model.Magasin;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import com.fastmarket.fastmarket_api.repository.MagasinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MagasinRepository magasinRepository;

    // Récupérer tous les clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // créer un client
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    // Vérifier si un client existe déjà par email
    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody Client client) {
        return clientRepository.findByEmailAndMotDePasse(
                        client.getEmail(),
                        client.getMotDePasse()
                ).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // Mettre à jour le magsin d'un client
    @PutMapping("/modifierMagasin")
    public ResponseEntity<String> modifierMagasinClient(@RequestBody ModifierMagasinClientRequest req) {
        Client client = clientRepository.findById(req.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        Magasin magasin = magasinRepository.findById(req.getMagasinId())
                .orElseThrow(() -> new RuntimeException("Magasin introuvable"));

        client.setMagasin(magasin);
        clientRepository.save(client);

        return ResponseEntity.ok("Magasin modifié avec succès pour le client.");
    }

    // Récupérer un client par email
    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        return clientRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
