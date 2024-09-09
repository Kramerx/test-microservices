package com.project.transactional.service;

import com.project.transactional.dto.AccountReportDTO;
import com.project.transactional.dto.AccountStatusReportDTO;
import com.project.transactional.dto.ClientDTO;
import com.project.transactional.dto.MovementReportDTO;
import com.project.transactional.model.Account;
import com.project.transactional.model.Movement;
import com.project.transactional.repository.AccountRepository;
import com.project.transactional.repository.MovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to generate a report.
 */
@Service
@Transactional
public class ReportService {
    private final Logger log = LoggerFactory.getLogger(ReportService.class);

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

    @Transactional()
    public AccountStatusReportDTO generateAccountStatusReport(
            Long idClient,
            LocalDate initDate,
            LocalDate endDate) {

        List<Account> clientAccounts = accountRepository.findByIdClient(idClient);
        List<AccountReportDTO> accountReportList = new ArrayList<>();

        clientAccounts.forEach(account -> {
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
        });
        try {
            String clientName = getClientName(idClient).toFuture().get();
            return new AccountStatusReportDTO(idClient, clientName, accountReportList);
        } catch (Exception e) {
            throw new RuntimeException("Lo sentimos, tenemos problemas al generar el reporte");
        }
    }

    public Mono<String> getClientName(Long idClient) {
        return webClient.get()
                .uri("/client/{id}", idClient)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .map(apiResponse -> {
                    if (apiResponse.getName() != null) {
                        return apiResponse.getName();
                    }
                    return "";
                })
                .doOnError(error -> {
                    throw new RuntimeException(error.getMessage());
                });
    }

    private MovementReportDTO passToMovementReport(Movement movement) {
        return new MovementReportDTO(
                movement.getCreationDate(),
                movement.getTypeMovement(),
                movement.getAmount(),
                movement.getFinalBalance());
    }
}
