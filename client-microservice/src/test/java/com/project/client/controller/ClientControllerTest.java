package com.project.client.controller;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.project.client.model.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.project.client.service.ClientService;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    void whenGetAllClient_thenGetList() throws Exception {
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
        Mockito.when(clientService.findAll()).thenReturn(listClients);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idClient", is(1)))
                .andExpect(jsonPath("$[1].idClient", is(2)));
    }
}
