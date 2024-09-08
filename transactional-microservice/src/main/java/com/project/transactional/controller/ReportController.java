package com.project.transactional.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.transactional.dto.AccountStatusReportDTO;
import com.project.transactional.service.ReportService;

/**
 * Controller to reports
 */
@RestController
@RequestMapping("/reportes")
public class ReportController {
    private final Logger log = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAccountStatusReport(
            @RequestParam Long idClient,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("[getAccountStateReport] Generate a report to client: {}", idClient);

        if (idClient == null || initDate == null || endDate == null) {
            return ResponseEntity.badRequest()
                    .body("Lo sentimos, existen campos obligatorios que no se estan enviando: cliente id, fecha iniciar o fecha final.");
        }

        if (initDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body("La fecha de inicio debe ser menor a la fecha de fin.");
        }

        try {
            AccountStatusReportDTO report = reportService.generateAccountStatusReport(idClient, initDate,
                    endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            log.info("[getAccountStateReport] Error in generate report {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Lo sentimos hubo un problema generar el reporte ");
        }
    }
}
