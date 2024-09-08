package com.project.client.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TRA_PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends DateEntity {

    @Id
    @Column(name = "ID_PERSON", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identification;

    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "GENDER", nullable = false)
    private String gender;
    @Column(name = "AGE", nullable = false)
    private Integer age;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "PHONE", nullable = false)
    private String phone;

    public Long getIdentification() {
        return identification;
    }

    public void setIdentification(Long identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}