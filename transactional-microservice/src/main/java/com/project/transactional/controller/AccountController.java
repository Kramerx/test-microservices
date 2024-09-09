package com.project.transactional.controller;

import com.project.transactional.model.Account;
import com.project.transactional.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for manage accounts.
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        log.info("[getAllAccounts] Get all accounts.");
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@NonNull @PathVariable Long id) {
        log.info("[GetByIdAccount] Get account by id = {}.", id);
        return accountService.findAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        log.info("[createAccount] Add new account.");
        return accountService.saveAccount(account);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@NonNull @PathVariable Long id, @RequestBody Account account) {
        log.info("[editAccount] Edit an account.");
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@NonNull @PathVariable Long id) {
        log.info("[deleteAccount] Delete an account.");
        accountService.deleteAccount(id);
    }

}