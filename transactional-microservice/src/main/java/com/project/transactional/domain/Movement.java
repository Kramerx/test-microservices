package com.project.transactional.domain;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movement {

    private Long idMovement;
    private String typeMovement;
    private BigDecimal amount;
    private BigDecimal finalBalance;
    private java.util.Date creationDate;
    private Account account;

}
