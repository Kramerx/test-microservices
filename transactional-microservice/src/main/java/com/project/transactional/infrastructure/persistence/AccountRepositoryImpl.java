package com.project.transactional.infrastructure.persistence;

import com.project.transactional.domain.Account;
import com.project.transactional.domain.repository.AccountRepository;
import com.project.transactional.infrastructure.persistence.entity.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public List<Account> findByIdClient(Long idClient) {
        return jpaAccountRepository.findByIdClient(idClient)
                .stream()
                .map(AccountEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAll() {
        return jpaAccountRepository.findAll()
                .stream()
                .map(AccountEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> findById(Long idAccount) {
        return jpaAccountRepository.findById(idAccount)
                .map(AccountEntity::toDomain);
    }

    @Override
    public Optional<Account> findByNumberAccount(String numberAccount) {
        return jpaAccountRepository.findByNumberAccount(numberAccount)
                .map(AccountEntity::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountEntity.fromDomain(account);
        return jpaAccountRepository.save(entity).toDomain();
    }

    @Override
    public void deleteById(Long idAccount) {
        jpaAccountRepository.deleteById(idAccount);
    }
}