package com.project.client.service;

import java.util.List;
import java.util.Optional;

import com.project.client.model.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.client.repository.ClientRepository;

/**
 * Service to manage clients.
 */
@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findClientByIdentification(Long id) {
        return clientRepository.findByIdentification(id);
    }

    public Client updateClient(Long id, Client client) {
        client.setIdClient(id);
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
