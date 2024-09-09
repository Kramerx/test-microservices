package com.project.transactional.service;

import com.project.transactional.exception.NotBalanceException;
import com.project.transactional.model.Account;
import com.project.transactional.model.Movement;
import com.project.transactional.repository.AccountRepository;
import com.project.transactional.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service to manage movements.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MovementService {
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<Movement> findAll() {
        return movementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Movement> findMovementById(Long id) {
        return movementRepository.findById(id);
    }

    @Transactional()
    public Movement saveMovement(Movement movement) {
        Account account = accountRepository.findByNumberAccount(movement.getAccount().getNumberAccount())
                .orElseThrow(() -> new RuntimeException("Lo sentimos, no pudimos encontrar el número de cuenta ingresado"));

        BigDecimal updateBalance = account.getInitialBalance().add(movement.getAmount());
        if (updateBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotBalanceException("Lo sentimos, no tiene saldo disponible");
        }

        if (movement.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            movement.setTypeMovement("D");
        } else {
            movement.setTypeMovement("C");
        }

        account.setInitialBalance(updateBalance);
        accountRepository.save(account);
        movement.setFinalBalance(updateBalance);
        return movementRepository.save(movement);
    }

    @Transactional()
    public void deleteMovement(Long id) {
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lo sentimos, tuvimos un problema eliminando el movimiento"));
        Account account = accountRepository.findByNumberAccount(movement.getAccount().getNumberAccount())
                .orElseThrow(() -> new RuntimeException("Lo sentimos, no pudimos encontrar el número de cuenta ingresado"));
        BigDecimal updateBalance = account.getInitialBalance().subtract(movement.getAmount());
        account.setInitialBalance(updateBalance);
        Movement newMovement = new Movement();
        newMovement.setFinalBalance(updateBalance);
        newMovement.setAmount(movement.getAmount().multiply(new BigDecimal(-1)));
        newMovement.setAccount(account);
        if (newMovement.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            newMovement.setTypeMovement("D");
        } else {
            newMovement.setTypeMovement("C");
        }
        accountRepository.save(account);
        movementRepository.save(newMovement);
    }
}
