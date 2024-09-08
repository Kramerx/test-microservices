package com.project.client.service;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.client.model.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.client.repository.ClientRepository;

@SpringBootTest
class ClientServiceTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    void whenGetAllClient_thenGetList() {
        Client client1 = new Client();
        client1.setIdClient(1L);
        client1.setPassword("pass123");
        client1.setStateClient(true);
        client1.setName("Name1");
        client1.setGender("Male");
        client1.setAge(30);
        client1.setIdentification(123456789L);
        client1.setAddress("Cuenca1");
        client1.setPhone("0987654321");

        Client client2 = new Client();
        client2.setIdClient(2L);
        client2.setPassword("pass123");
        client2.setStateClient(false);
        client2.setName("Name2");
        client2.setGender("Female");
        client2.setAge(30);
        client2.setIdentification(987654321L);
        client2.setAddress("Cuenca2");
        client2.setPhone("0987654321");

        List<Client> listClients = Arrays.asList(client1, client2);
        Mockito.when(clientRepository.findAll()).thenReturn(listClients);

        List<Client> result = clientService.findAll();

        assertEquals(2, result.size(), "Is necessary a list with 2 clients");
        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
    }
}