package com.project.client.service;

import com.project.client.model.Client;
import com.project.client.repository.ClientRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

/**
 * Service to manage clients.
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientRepository.class);

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        log.info("[findAll] Fetching all clients");
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            log.warn("[findAll] No clients found");
            return List.of();
        }
        log.info("[findAll] Found {} clients", clients.size());
        return clients;
    }

    @Transactional(readOnly = true)
    public Optional<Client> findClientById(Long id) {
        log.info("[findClientById] Fetching client with ID: {}", id);
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            log.error("[findClientById] Client not found with ID: {}", id);
            throw new RuntimeException("Lo sentimos, no pudimos encontrar el cliente ingresado");
        }
        log.info("[findClientById] Client found with ID: {}", id);
        return client;
    }

    @Transactional(readOnly = true)
    public Optional<Client> findClientByIdentification(String id) {
        log.info("[findClientByIdentification] Fetching client with identification: {}", id);
        Optional<Client> client = clientRepository.findByIdentification(id);
        if (client.isEmpty()) {
            log.error("[findClientByIdentification] Client not found with identification: {}", id);
            throw new RuntimeException("Lo sentimos, no pudimos encontrar el cliente ingresado");
        }
        log.info("[findClientByIdentification] Client found with identification: {}", id);
        return client;
    }

    @Transactional
    public Client saveClient(Client client) {
        log.info("[saveClient] Starting to save client with identification: {}", client.getIdentification());
        Optional<Client> existingClient = clientRepository.findByIdentification(client.getIdentification());
        if (existingClient.isPresent()) {
            log.error("[saveClient] Client with identification {} already exists", client.getIdentification());
            throw new RuntimeException("Lo sentimos, el cliente ya existe");
        }
        log.info("[saveClient] Client with identification {} saved successfully", client.getIdentification());
        return clientRepository.save(client);
    }

    @Transactional
    public Client updateClient(Long id, Client client) {
        log.info("[updateClient] Starting to update client with ID: {}", id);
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[updateClient] Client not found with ID: {}", id);
                    return new RuntimeException("Lo sentimos, no pudimos encontrar el cliente ingresado");
                });
        log.info("[updateClient] Client with ID: {} updated successfully", id);
        existingClient.setName(client.getName());
        existingClient.setYearBirth(client.getYearBirth());
        existingClient.setAddress(client.getAddress());
        existingClient.setPhone(client.getPhone());
        existingClient.setGender(client.getGender());

        return clientRepository.save(existingClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        log.info("[deleteClient] Starting to delete client with ID: {}", id);
        clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[deleteClient] Client not found with ID: {}", id);
                    return new RuntimeException("Lo sentimos, tuvimos un problema eliminando el cliente");
                });
        clientRepository.deleteById(id);
        log.info("[deleteClient] Client with ID: {} deleted successfully", id);
    }
}
