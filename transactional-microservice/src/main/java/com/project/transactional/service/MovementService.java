package com.project.transactional.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.transactional.exception.NotBalanceException;
import com.project.transactional.model.Account;
import com.project.transactional.model.Movement;
import com.project.transactional.repository.AccountRepository;
import com.project.transactional.repository.MovementRepository;

/**
 * Service to manage movements.
 */
@Service
@Transactional
public class MovementService {
	private final MovementRepository movementRepository;
	private final AccountRepository accountRepository;

	public MovementService(MovementRepository movementRepository, AccountRepository accountRepository) {
		this.movementRepository = movementRepository;
		this.accountRepository = accountRepository;
	}

	public List<Movement> findAll() {
		return movementRepository.findAll();
	}

	public Movement saveMovement(Movement movement) {
		Account account = accountRepository.findByNumberAccount(movement.getAccount().getNumberAccount())
				.orElseThrow(() -> new RuntimeException("Lo sentimos, no pudimos encontrar el número de cuenta ingresado"));

		BigDecimal updateBalance = account.getInitialBalance().add(movement.getAmount());

		if (updateBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new NotBalanceException("Lo sentimos, no tiene saldo disponible");
		}

		accountRepository.save(account);
		movement.setFinalBalance(updateBalance);
		return movementRepository.save(movement);
	}

	public Optional<Movement> findMovementById(Long id) {
		return movementRepository.findById(id);
	}

	public Movement updateMovement(Long id, Movement movement) {
		movement.setIdMovement(id);
		return movementRepository.save(movement);
	}

	public void deleteMovement(Long id) {
		movementRepository.deleteById(id);
	}

}
