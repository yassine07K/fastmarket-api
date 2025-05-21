package com.fastmarket.fastmarket_api.controller;

import com.fastmarket.fastmarket_api.model.Client;
import com.fastmarket.fastmarket_api.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "*") // autorise les appels depuis React ou Postman
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    // GET /clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // POST /clients
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody Client client) {
        return clientRepository.findByEmailAndMotDePasse(
                        client.getEmail(),
                        client.getMotDePasse()
                ).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
