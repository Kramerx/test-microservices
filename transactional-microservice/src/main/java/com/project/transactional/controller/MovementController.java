package com.project.transactional.controller;

import com.project.transactional.model.Movement;
import com.project.transactional.service.MovementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for movement of accounts.
 */
@RestController
@RequestMapping("/movement")
@RequiredArgsConstructor
public class MovementController {
    private final Logger log = LoggerFactory.getLogger(MovementController.class);

    private final MovementService movementService;

    @GetMapping
    public List<Movement> getAllMovements() {
        log.info("[getAllMovements] Get movements.");
        return movementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@NonNull @PathVariable Long id) {
        return movementService.findMovementById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movement> createMovement(@NonNull @RequestBody Movement movement) {
        log.info("[createMovement] Add movements.");
        Movement nuevoMovement = movementService.saveMovement(movement);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMovement);
    }

    @DeleteMapping("/{id}")
    public void deleteMovement(@NonNull @PathVariable Long id) {
        movementService.deleteMovement(id);
    }
}
