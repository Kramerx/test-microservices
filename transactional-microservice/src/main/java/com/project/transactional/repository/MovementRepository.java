package com.project.transactional.repository;

import com.project.transactional.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByAccountIdAccountAndCreationDateBetween(Long idAccount, Date initDate, Date endDate);
}