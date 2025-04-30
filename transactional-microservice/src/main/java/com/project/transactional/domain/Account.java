package com.project.transactional.domain;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    private Long idAccount;
    private String numberAccount;
    private String typeAccount;
    private BigDecimal initialBalance;
    private Integer state;
    private Long idClient;
    private Date creationDate;

}