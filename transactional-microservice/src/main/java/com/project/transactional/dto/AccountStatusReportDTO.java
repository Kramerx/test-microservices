package com.project.transactional.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AccountStatusReportDTO {
    private Long idClient;
    private String clientName;
    private List<AccountReportDTO> accounts;

    public AccountStatusReportDTO(Long idClient, String clientName, List<AccountReportDTO> accounts) {
        this.idClient = idClient;
        this.clientName = clientName;
        this.accounts = accounts;
    }

}