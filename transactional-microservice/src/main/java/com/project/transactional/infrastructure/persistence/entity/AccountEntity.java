package com.project.transactional.infrastructure.persistence.entity;

import com.project.transactional.domain.Account;
import com.project.transactional.shared.Utils;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRA_ACCOUNT")
public class AccountEntity {

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
    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @PrePersist
    protected void onPrePersist() {
        if (this.creationDate == null) {
            this.creationDate = new Date(); // Lógica de onCreate
        }
        if (this.numberAccount == null) {
            this.numberAccount = Utils.generateUniqueId().toString(); // Lógica de generateId
        }
    }

    public static AccountEntity fromDomain(Account account) {
        return AccountEntity.builder()
                .idAccount(account.getIdAccount())
                .numberAccount(account.getNumberAccount())
                .typeAccount(account.getTypeAccount())
                .initialBalance(account.getInitialBalance())
                .state(account.getState())
                .idClient(account.getIdClient())
                .creationDate(account.getCreationDate())
                .build();
    }

    public Account toDomain() {
        return Account.builder()
                .idAccount(this.idAccount)
                .numberAccount(this.numberAccount)
                .typeAccount(this.typeAccount)
                .initialBalance(this.initialBalance)
                .state(this.state)
                .idClient(this.idClient)
                .creationDate(this.creationDate)
                .build();
    }

}
