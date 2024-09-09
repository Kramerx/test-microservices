package com.project.transactional.service;

import com.project.transactional.model.Account;
import com.project.transactional.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service to manage accounts.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Account> findAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Account> findAccountsByIdClient(Long idClient) {
        return accountRepository.findByIdClient(idClient);
    }

    @Transactional()
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional()
    public Account updateAccount(Long id, Account account) {
        account.setIdAccount(id);
        return accountRepository.save(account);
    }

    @Transactional()
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}