package com.project.transactional.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Setter
@Getter
@MappedSuperclass
public abstract class DateEntity {

    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = new java.util.Date();
    }

}
