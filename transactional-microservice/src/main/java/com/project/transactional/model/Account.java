package com.project.transactional.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRA_ACCOUNT")
public class Account extends DateEntity {

    @Id
    @Column(name = "ID_ACCOUNT", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;

    @Column(name = "NUMBER_ACCOUNT", nullable = false)
    private Long numberAccount;
    @Column(name = "TYPE_ACCOUNT", nullable = false)
    private String typeAccount;
    @Column(name = "INITIAL_BALANCE", nullable = false)
    private BigDecimal initialBalance;
    @Column(name = "STATE_ACCOUNT", nullable = false)
    private Integer state;
    @Column(name = "ID_CLIENT", nullable = false)
    private Long idClient;

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public Long getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(Long numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
}