package com.project.transactional.repository;

import com.project.transactional.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByIdClient(Long idClient);
    Optional<Account> findByNumberAccount(String numberAccount);
}