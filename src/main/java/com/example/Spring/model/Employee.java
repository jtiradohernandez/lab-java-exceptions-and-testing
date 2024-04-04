package com.example.Spring.model;

import com.example.Spring.EmployeeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    @NotEmpty(message = "Department cannot be empty")
    private String department;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private EmployeeStatus status;

    public Employee(String department, String name, EmployeeStatus status) {
        this.department = department;
        this.name = name;
        this.status = status;
    }
}
