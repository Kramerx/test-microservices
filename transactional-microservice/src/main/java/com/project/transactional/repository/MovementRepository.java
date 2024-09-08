package com.project.transactional.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.transactional.model.Movement;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByAccountIdAccountAndCreationDateBetween(Long idAccount, Date initDate, Date endDate);
}