package com.project.transactional.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class AccountReportDTO {
    private String accountNumber;
    private String typeAccount;
    private BigDecimal initialBalance;
    private List<MovementReportDTO> movements;

    public AccountReportDTO(String accountNumber, String typeAccount, BigDecimal initialBalance,
                            List<MovementReportDTO> movements) {
        super();
        this.accountNumber = accountNumber;
        this.typeAccount = typeAccount;
        this.initialBalance = initialBalance;
        this.movements = movements;
    }

}
