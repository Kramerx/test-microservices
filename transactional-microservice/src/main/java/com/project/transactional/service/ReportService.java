package com.project.transactional.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.project.transactional.dto.AccountReportDTO;
import com.project.transactional.dto.MovementReportDTO;
import com.project.transactional.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.transactional.dto.AccountStatusReportDTO;
import com.project.transactional.model.Movement;
import com.project.transactional.repository.AccountRepository;
import com.project.transactional.repository.MovementRepository;

/**
 * Servicio para la generación de reportes de estado de cuenta.
 */
@Service
@Transactional
public class ReportService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final WebClient webClient;

    public ReportService(
            AccountRepository accountRepository,
            MovementRepository movementRepository,
            WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
    }


    public AccountStatusReportDTO generateAccountStatusReport(
            Long idClient,
            LocalDate initDate,
            LocalDate endDate) {
        String clientName = getClientName(idClient);
        List<Account> clientAccounts = accountRepository.findByIdClient(idClient);
        List<AccountReportDTO> accountReportList = new ArrayList<>();

        for (Account account : clientAccounts) {
            Date startDate = java.sql.Date.valueOf(initDate);
            Date finishDate = java.sql.Date.valueOf(endDate);

            List<Movement> accountMovements = movementRepository.findByAccountIdAccountAndCreationDateBetween(
                    account.getIdAccount(), startDate, finishDate);

            List<MovementReportDTO> movementsReport = accountMovements.stream()
                    .map(this::passToMovementReport)
                    .collect(Collectors.toList());

            AccountReportDTO accountReport = new AccountReportDTO(
                    account.getNumberAccount(),
                    account.getTypeAccount(),
                    account.getInitialBalance(),
                    movementsReport);

            accountReportList.add(accountReport);
        }

        return new AccountStatusReportDTO(idClient, clientName, accountReportList);
    }

    private String getClientName(Long idClient) {
        try {
            Map client = webClient.get()
                    .uri("/clientes/{id}", idClient)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            return (String) client.get("name");
        } catch (Exception e) {
            return "NA";
        }

    }

    private MovementReportDTO passToMovementReport(Movement movement) {
        return new MovementReportDTO(
                movement.getCreationDate(),
                movement.getTypeMovement(),
                movement.getAmount(),
                movement.getFinalBalance());
    }
}
