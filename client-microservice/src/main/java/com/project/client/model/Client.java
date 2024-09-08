package com.project.client.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TRA_CLIENT")
public class Client extends Person {

    @Column(name = "ID_CLIENT", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "STATE_CLIENT", nullable = false)
    private Boolean stateClient;

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStateClient() {
        return stateClient;
    }

    public void setStateClient(Boolean stateClient) {
        this.stateClient = stateClient;
    }
}