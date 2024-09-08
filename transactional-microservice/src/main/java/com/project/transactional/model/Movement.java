package com.project.transactional.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRA_MOVEMENT")
public class Movement extends DateEntity {

    @Id
    @Column(name = "ID_MOVEMENT", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovement;

    @Column(name = "TYPE_MOVEMENT", nullable = false)
    private String typeMovement;
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    @Column(name = "FINAL_BALANCE", nullable = false)
    private BigDecimal finalBalance;

    @ManyToOne
    @JoinColumn(name = "ID_ACCOUNT", nullable = false, referencedColumnName = "ID_ACCOUNT")
    private Account account;

    public Long getIdMovement() {
        return idMovement;
    }

    public void setIdMovement(Long idMovement) {
        this.idMovement = idMovement;
    }

    public String getTypeMovement() {
        return typeMovement;
    }

    public void setTypeMovement(String typeMovement) {
        this.typeMovement = typeMovement;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(BigDecimal finalBalance) {
        this.finalBalance = finalBalance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
