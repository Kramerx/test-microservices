package com.project.transactional.domain.repository;

import com.project.transactional.domain.Movement;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MovementRepository {
    List<Movement> findByAccountIdAccountAndCreationDateBetween(Long idAccount, Date initDate, Date endDate);
    List<Movement> findAll();
    Optional<Movement> findById(Long id);
    Movement save(Movement movement);
}