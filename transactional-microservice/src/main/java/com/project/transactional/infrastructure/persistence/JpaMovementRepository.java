package com.project.transactional.infrastructure.persistence;

import com.project.transactional.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JpaMovementRepository extends JpaRepository<MovementEntity, Long> {
    List<MovementEntity> findByAccountIdAccountAndCreationDateBetween(Long idAccount, Date initDate, Date endDate);
}