package com.project.transactional.application;

import com.project.transactional.application.dto.AccountReportDTO;
import com.project.transactional.application.dto.AccountStatusReportDTO;
import com.project.transactional.application.dto.ClientDTO;
import com.project.transactional.application.dto.MovementReportDTO;
import com.project.transactional.domain.Account;
import com.project.transactional.domain.Movement;
import com.project.transactional.domain.repository.AccountRepository;
import com.project.transactional.domain.repository.MovementRepository;
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
        log.info("[generateAccountStatusReport] Generating account status report for client ID: {} between {} and {}", idClient, initDate, endDate);
        List<Account> clientAccounts = accountRepository.findByIdClient(idClient);
        if (clientAccounts.isEmpty()) {
            log.error("[generateAccountStatusReport] No accounts found for client ID: {}", idClient);
            throw new RuntimeException("Lo sentimos, no pudimos encontrar cuentas para el cliente ingresado");
        }

        List<AccountReportDTO> accountReportList = new ArrayList<>();

        clientAccounts.forEach(account -> {
            Date startDate = java.sql.Date.valueOf(initDate);
            Date finishDate = java.sql.Date.valueOf(endDate);
            List<Movement> accountMovements = movementRepository.findByAccountIdAccountAndCreationDateBetween(
                    account.getIdAccount(), startDate, finishDate);
            if (accountMovements.isEmpty()) {
                log.warn("[generateAccountStatusReport] No movements found for account ID: {}", account.getIdAccount());
            } else {
                List<MovementReportDTO> movementsReport = accountMovements.stream()
                        .map(this::passToMovementReport)
                        .collect(Collectors.toList());
                AccountReportDTO accountReport = new AccountReportDTO(
                        account.getNumberAccount(),
                        account.getTypeAccount(),
                        account.getInitialBalance(),
                        movementsReport);
                accountReportList.add(accountReport);
                log.info("[generateAccountStatusReport] Account report generated for account ID: {}", account.getIdAccount());
            }
        });
        try {
            String clientName = getClientName(idClient).toFuture().get();
            if (clientName.isEmpty()) {
                log.error("[generateAccountStatusReport] Client name not found for ID: {}", idClient);
                throw new RuntimeException("Lo sentimos, no pudimos encontrar el nombre del cliente ingresado");
            }
            log.info("[generateAccountStatusReport] Report generated successfully for client ID: {}", idClient);
            return new AccountStatusReportDTO(idClient, clientName, accountReportList);
        } catch (Exception e) {
            log.error("[generateAccountStatusReport] Error generating report: {}", e.getMessage());
            throw new RuntimeException("Lo sentimos, tenemos problemas al generar el reporte");
        }
    }

    public Mono<String> getClientName(Long idClient) {
        log.info("[getClientName] Fetching client name for ID: {}", idClient);
        return webClient.get()
                .uri("/client/{id}", idClient)
                .retrieve()
                .bodyToMono(ClientDTO.class)
                .map(apiResponse -> {
                    if (apiResponse.getName() != null) {
                        log.info("[getClientName] Client name fetched successfully: {}", apiResponse.getName());
                        return apiResponse.getName();
                    }
                    log.warn("[getClientName] Client name is null for ID: {}", idClient);
                    return "";
                })
                .doOnError(error -> {
                    log.error("[getClientName] Error fetching client name: {}", error.getMessage());
                    throw new RuntimeException(error.getMessage());
                });
    }

    private MovementReportDTO passToMovementReport(Movement movement) {
        log.info("[passToMovementReport] Converting movement to report DTO: {}", movement.getIdMovement());
        return new MovementReportDTO(
                movement.getCreationDate(),
                movement.getTypeMovement(),
                movement.getAmount(),
                movement.getFinalBalance());
    }
}
