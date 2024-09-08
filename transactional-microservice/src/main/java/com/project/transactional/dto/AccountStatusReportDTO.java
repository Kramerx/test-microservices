package com.project.transactional.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AccountStatusReportDTO {
    private Long idClient;
    private String clientName;
    private List<AccountReportDTO> accounts;

    public AccountStatusReportDTO(Long idClient, String clientName, List<AccountReportDTO> accounts) {
        this.idClient = idClient;
        this.clientName = clientName;
        this.accounts = accounts;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
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