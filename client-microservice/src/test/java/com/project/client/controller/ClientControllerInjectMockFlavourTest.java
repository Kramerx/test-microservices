package com.project.client.controller;

import com.project.client.model.Client;
import com.project.client.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClientControllerInjectMockFlavourTest {
    @InjectMocks
    private ClientController clientController;
    @Mock
    private ClientService clientService;

    @Test
    public void whenGetAllClient_thenGetList() {
        Client client1 = new Client();
        client1.setClientId(1L);
        client1.setPassword("pass123");
        client1.setStateClient(true);
        client1.setName("Name1");
        client1.setGender("Male");
        client1.setYearBirth(1994);
        client1.setIdentification("1234567890");
        client1.setAddress("Cuenca1");
        client1.setPhone("0987654321");

        Client client2 = new Client();
        client2.setClientId(2L);
        client2.setPassword("pass123");
        client2.setStateClient(false);
        client2.setName("Name2");
        client2.setGender("Female");
        client2.setYearBirth(1990);
        client2.setIdentification("9876543210");
        client2.setAddress("Cuenca2");
        client2.setPhone("0987654321");

        List<Client> listClients = Arrays.asList(client1, client2);
        Mockito.when(clientService.findAll()).thenReturn(listClients);
        List<Client> allClients = clientController.getAllClients();
        Assertions.assertEquals(1, allClients.get(0).getClientId());
        Assertions.assertEquals(2, allClients.get(1).getClientId());
    }
}