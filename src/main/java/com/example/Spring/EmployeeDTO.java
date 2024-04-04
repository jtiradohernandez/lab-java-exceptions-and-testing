package com.example.Spring;

import com.example.Spring.model.Patient;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Optional;

@Data
public class EmployeeDTO {
    EmployeeStatus status;
    String department;
}
