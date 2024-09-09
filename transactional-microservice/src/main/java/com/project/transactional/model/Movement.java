package com.project.transactional.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
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
    @Column(name = "FINAL_BALANCE")
    private BigDecimal finalBalance;

    @ManyToOne
    @JoinColumn(name = "ID_ACCOUNT", nullable = false, referencedColumnName = "ID_ACCOUNT")
    private Account account;

}
