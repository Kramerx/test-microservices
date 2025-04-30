package com.project.transactional.infrastructure.persistence;

import com.project.transactional.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByIdClient(Long idClient);
    Optional<AccountEntity> findByNumberAccount(String numberAccount);
}