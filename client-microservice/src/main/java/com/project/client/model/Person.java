package com.project.client.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "TRA_PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends DateEntity {

    @Id
    @Column(name = "ID_PERSON")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(name = "IDENTIFICATION", length = 10, nullable = false)
    private String identification;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "GENDER", nullable = false)
    private String gender;
    @Column(name = "YEAR_BIRTH", nullable = false)
    private Integer yearBirth;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "PHONE", nullable = false)
    private String phone;

}