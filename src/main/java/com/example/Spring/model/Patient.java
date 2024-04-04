package com.example.Spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    //@Past(message = "Date should be a previous date")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee admittedBy;


    public Patient(String name, Date dateOfBirth, Employee admittedBy) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.admittedBy = admittedBy;
    }
}
