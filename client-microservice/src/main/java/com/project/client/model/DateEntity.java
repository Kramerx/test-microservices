package com.project.client.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class DateEntity {

    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
    }

}
