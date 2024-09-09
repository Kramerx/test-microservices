package com.project.transactional.model;

import com.project.transactional.util.Utils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
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
    private String numberAccount;
    @Column(name = "TYPE_ACCOUNT", nullable = false)
    private String typeAccount;
    @Column(name = "INITIAL_BALANCE", nullable = false)
    private BigDecimal initialBalance;
    @Column(name = "STATE_ACCOUNT", nullable = false)
    private Integer state;
    @Column(name = "ID_CLIENT", nullable = false)
    private Long idClient;

    @PrePersist
    protected void generateId() {
        this.numberAccount = Utils.generateUniqueId().toString();
    }

}