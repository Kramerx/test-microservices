package com.project.transactional.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.transactional.model.Movement;
import com.project.transactional.service.MovementService;

/**
 * Controller for movement of accounts.
 */
@RestController
@RequestMapping("/movimientos")
public class MovementController {
    private final Logger log = LoggerFactory.getLogger(MovementController.class);

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    public List<Movement> getAllMovements() {
        log.info("[getAllMovements] Obteniendo todos los movimientos.");
        return movementService.findAll();
    }

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody Movement movement) {
        log.info("[createMovement] Creando movimientos.");
        Movement nuevoMovement = movementService.saveMovement(movement);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMovement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@PathVariable Long id) {
        return movementService.findMovementById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Movement updateMovement(@PathVariable Long id, @RequestBody Movement movement) {
        return movementService.updateMovement(id, movement);
    }

    @DeleteMapping("/{id}")
    public void deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
    }
}
