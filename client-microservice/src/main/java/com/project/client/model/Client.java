package com.project.client.model;

import com.project.client.util.Utils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "TRA_CLIENT")
public class Client extends Person {

    @Column(name = "ID_CLIENT", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "STATE_CLIENT", nullable = false)
    private Boolean stateClient;

    @PrePersist
    protected void generateClientId() {
        this.stateClient = true;
        this.clientId = Utils.generateUniqueId();
    }

}