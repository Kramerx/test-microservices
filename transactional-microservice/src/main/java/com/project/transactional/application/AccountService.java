package com.project.transactional.application;

import com.project.transactional.domain.Account;
import com.project.transactional.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service to manage accounts.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<Account> findAll() {
        log.info("[findAll] Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            log.warn("[findAll] No accounts found");
            return List.of();
        }
        log.info("[findAll] Found {} accounts", accounts.size());
        return accounts;
    }

    @Transactional(readOnly = true)
    public Optional<Account> findAccountById(Long id) {
        log.info("[getAccountById] Fetching account with ID: {}", id);
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            log.error("[getAccountById] Account not found with ID: {}", id);
            throw new RuntimeException("Lo sentimos, no pudimos encontrar la cuenta ingresada");
        }
        log.info("[getAccountById] Account found with ID: {}", id);
        return account;
    }

    @Transactional(readOnly = true)
    public List<Account> findAccountsByIdClient(Long idClient) {
        log.info("[findAccountsByIdClient] Fetching account with ClientID: {}", idClient);
        List<Account> accounts = accountRepository.findByIdClient(idClient);
        if (accounts.isEmpty()) {
            log.error("[findAccountsByIdClient] No accounts found for ClientID: {}", idClient);
            return List.of();
        }
        log.info("[findAccountsByIdClient] Found {} accounts for ClientID: {}", accounts.size(), idClient);
        return accounts;
    }

    @Transactional()
    public Account saveAccount(Account account) {
        log.info("[saveAccount] Starting to save account for client: {}", account.getIdClient());
        return accountRepository.save(account);
    }

    @Transactional()
    public Account updateAccount(Long id, Account account) {
        log.info("[updateAccount] Starting to update account with ID: {}", id);
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[updateAccount] Account not found with ID: {}", id);
                    return new RuntimeException("Lo sentimos, no pudimos encontrar la cuenta ingresada");
                });
        existingAccount.setNumberAccount(account.getNumberAccount());
        existingAccount.setTypeAccount(account.getTypeAccount());
        existingAccount.setState(account.getState());
        existingAccount.setIdClient(account.getIdClient());
        log.info("[updateAccount] Account updated successfully with ID: {}", id);
        return accountRepository.save(existingAccount);

    }

    @Transactional()
    public void deleteAccount(Long id) {
        log.info("[deleteAccount] Starting to delete account with ID: {}", id);
        accountRepository.findById(id).orElseThrow(() -> {
            log.error("[deleteAccount] Account not found with ID: {}", id);
            return new RuntimeException("Lo sentimos, tuvimos un problema eliminando la cuenta");
        });
        accountRepository.deleteById(id);
        log.info("[deleteAccount] Account deleted successfully with ID: {}", id);
    }
}
