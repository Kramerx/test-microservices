package com.project.client.controller;

import com.project.client.model.Client;
import com.project.client.service.ClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Controller for manage clients.
 */
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        log.info("[getAllClients] Get all clients.");
        List<Client> clients = clientService.findAll();
        return clientService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@NonNull @PathVariable Long id) {
        log.info("[getClientById] Get client by id = {}.", id);
        return clientService.findClientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/identification/{id}")
    public ResponseEntity<Client> getClientByIdentification(@NonNull @PathVariable String id) {
        log.info("[getClientByIdentification] Get client by identification = {}.", id);
        return clientService.findClientByIdentification(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        log.info("[createClient] Add new client.");
        return clientService.saveClient(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@NonNull @PathVariable Long id, @RequestBody Client client) {
        log.info("[updateClient] Edit a client.");
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@NonNull @PathVariable Long id) {
        log.info("[deleteClient] Delete a client.");
        clientService.deleteClient(id);
    }
}
