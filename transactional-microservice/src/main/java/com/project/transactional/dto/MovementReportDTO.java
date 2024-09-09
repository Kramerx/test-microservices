package com.project.transactional.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class MovementReportDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private String typeMovement;
    private BigDecimal amount;
    private BigDecimal finalBalance;

    public MovementReportDTO(Date date, String typeMovement, BigDecimal amount, BigDecimal finalBalance) {
        super();
        this.date = date;
        this.typeMovement = typeMovement;
        this.amount = amount;
        this.finalBalance = finalBalance;
    }

}