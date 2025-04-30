package com.project.transactional.infrastructure.persistence;

import com.project.transactional.domain.Movement;
import com.project.transactional.domain.repository.MovementRepository;
import com.project.transactional.infrastructure.persistence.entity.MovementEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovementRepositoryImpl implements MovementRepository {

    private final JpaMovementRepository jpaMovementRepository;

    public MovementRepositoryImpl(JpaMovementRepository jpaMovementRepository) {
        this.jpaMovementRepository = jpaMovementRepository;
    }

    @Override
    public List<Movement> findByAccountIdAccountAndCreationDateBetween(Long idAccount, Date initDate, Date endDate) {
        return jpaMovementRepository.findByAccountIdAccountAndCreationDateBetween(idAccount,initDate,endDate)
                .stream()
                .map(MovementEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movement> findAll() {
        return jpaMovementRepository.findAll()
                .stream()
                .map(MovementEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movement> findById(Long idMovement) {
        return jpaMovementRepository.findById(idMovement)
                .map(MovementEntity::toDomain);
    }

    @Override
    public Movement save(Movement movement) {
        MovementEntity entity = MovementEntity.fromDomain(movement);
        return jpaMovementRepository.save(entity).toDomain();
    }

}