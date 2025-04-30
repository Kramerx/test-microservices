package com.project.transactional.application;

import com.project.transactional.domain.Account;
import com.project.transactional.domain.Movement;
import com.project.transactional.domain.repository.AccountRepository;
import com.project.transactional.domain.repository.MovementRepository;
import com.project.transactional.shared.exception.NotBalanceException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service to manage movements.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MovementService {

    private static final Logger log = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<Movement> findAll() {
        log.info("[findAll] Fetching all movements");
        List<Movement> movements = movementRepository.findAll();
        if (movements.isEmpty()) {
            log.warn("[findAll] No movements found");
            return List.of();
        }
        log.info("[findAll] Found {} movements", movements.size());
        return movements;
    }

    @Transactional(readOnly = true)
    public Optional<Movement> findMovementById(Long id) {
        log.info("[findMovementById] Fetching movement with ID: {}", id);
        Optional<Movement> movement = movementRepository.findById(id);
        if (movement.isEmpty()) {
            log.error("[findMovementById] Movement not found with ID: {}", id);
            throw new RuntimeException("Lo sentimos, no pudimos encontrar el movimiento ingresado");
        }
        log.info("[findMovementById] Movement found with ID: {}", id);
        return movement;
    }

    @Transactional()
    public Movement saveMovement(Movement movement) {
        log.info("[saveMovement] Starting to save movement for account: {}", movement.getAccount().getNumberAccount());
        Account account = accountRepository.findByNumberAccount(movement.getAccount().getNumberAccount())
                .orElseThrow(() -> {
                    log.error("[saveMovement] Account not found for number: {}", movement.getAccount().getNumberAccount());
                    return new RuntimeException("Lo sentimos, no pudimos encontrar el número de cuenta ingresado");
                });

        BigDecimal updateBalance = account.getInitialBalance().add(movement.getAmount());
        if (updateBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("[saveMovement] Insufficient balance for account: {}", account.getNumberAccount());
            throw new NotBalanceException("Lo sentimos, no tiene saldo disponible");
        }

        movement.setTypeMovement(movement.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "D" : "C");

        account.setInitialBalance(updateBalance);
        accountRepository.save(account);
        movement.setFinalBalance(updateBalance);
        log.info("[saveMovement] Movement saved successfully for account: {}", account.getNumberAccount());
        return movementRepository.save(movement);
    }

    @Transactional()
    public void deleteMovement(Long id) {
        log.info("[deleteMovement] Starting to delete movement with ID: {}", id);
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[deleteMovement] Movement not found with ID: {}", id);
                    return new RuntimeException("Lo sentimos, tuvimos un problema eliminando el movimiento");
                });
        Account account = accountRepository.findByNumberAccount(movement.getAccount().getNumberAccount())
                .orElseThrow(() -> {
                    log.error("[deleteMovement] Account not found for number: {}", movement.getAccount().getNumberAccount());
                    return new RuntimeException("Lo sentimos, no pudimos encontrar el número de cuenta ingresado");
                });
        BigDecimal updateBalance = account.getInitialBalance().subtract(movement.getAmount());
        account.setInitialBalance(updateBalance);

        Movement newMovement = new Movement();
        newMovement.setFinalBalance(updateBalance);
        newMovement.setAmount(movement.getAmount().negate());
        newMovement.setAccount(account);
        newMovement.setTypeMovement(newMovement.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "D" : "C");
        accountRepository.save(account);
        movementRepository.save(newMovement);
        log.info("[deleteMovement] Movement deleted successfully with ID: {}", id);
    }
}
