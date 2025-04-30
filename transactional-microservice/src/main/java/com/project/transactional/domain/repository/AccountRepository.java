package com.project.transactional.domain.repository;


import com.project.transactional.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findByIdClient(Long idClient);
    List<Account> findAll();
    Optional<Account> findById(Long idAccount);
    Optional<Account> findByNumberAccount(String numberAccount);
    Account save(Account account);
    void deleteById(Long idAccount);
}