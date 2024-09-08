package com.project.client.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@MappedSuperclass
public abstract class DateEntity {

    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date creationDate;
}
