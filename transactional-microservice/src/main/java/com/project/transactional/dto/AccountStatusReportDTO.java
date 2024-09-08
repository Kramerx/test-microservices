package com.project.transactional.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AccountStatusReportDTO {
    private Long clientId;
    private String clientName;
    private List<AccountReportDTO> accounts;

    public AccountStatusReportDTO(Long clientId, String clientName, List<AccountReportDTO> accounts) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.accounts = accounts;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<AccountReportDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountReportDTO> accounts) {
        this.accounts = accounts;
    }
}