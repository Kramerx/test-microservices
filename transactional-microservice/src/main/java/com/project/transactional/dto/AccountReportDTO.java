package com.project.transactional.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountReportDTO {
    private Long accountNumber;
    private String typeAccount;
    private BigDecimal initialBalance;
    private List<MovementReportDTO> movements;

    public AccountReportDTO(Long accountNumber, String typeAccount, BigDecimal initialBalance,
                            List<MovementReportDTO> movements) {
        super();
        this.accountNumber = accountNumber;
        this.typeAccount = typeAccount;
        this.initialBalance = initialBalance;
        this.movements = movements;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
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

    public List<MovementReportDTO> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementReportDTO> movements) {
        this.movements = movements;
    }
}
