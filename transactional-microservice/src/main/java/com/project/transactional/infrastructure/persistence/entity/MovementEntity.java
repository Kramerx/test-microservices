package com.project.transactional.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.util.Date;

import com.project.transactional.domain.Movement;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRA_MOVEMENT")
public class MovementEntity {

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
    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "ID_ACCOUNT", nullable = false, referencedColumnName = "ID_ACCOUNT")
    private AccountEntity account;
    
    public static MovementEntity fromDomain(Movement movement) {
        return MovementEntity.builder()
                .idMovement(movement.getIdMovement())
                .typeMovement(movement.getTypeMovement())
                .amount(movement.getAmount())
                .finalBalance(movement.getFinalBalance())
                .creationDate(movement.getCreationDate())
                .account(AccountEntity.fromDomain(movement.getAccount()))
                .build();
    }

    public Movement toDomain() {
        return Movement.builder()
                .idMovement(this.idMovement)
                .typeMovement(this.typeMovement)
                .amount(this.amount)
                .finalBalance(this.finalBalance)
                .creationDate(this.creationDate)
                .account(this.account.toDomain())
                .build();
    }
}
