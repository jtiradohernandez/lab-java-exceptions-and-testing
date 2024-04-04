package com.example.Spring.service;

import com.example.Spring.EmployeeStatus;
import com.example.Spring.model.Employee;
import com.example.Spring.model.Patient;
import com.example.Spring.repository.EmployeeRepository;
import com.example.Spring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientId(int ID) {
        return patientRepository.findByPatientId(ID);
    }

    public List<Patient> getPatientRangeDate(Date initDate, Date endDate) {
        return patientRepository.findByDateOfBirthBetween(initDate,endDate);
    }

    public List<Patient> getPatientByDoctorDepartment(Optional<String> department) {
        return patientRepository.findByDoctorDepartment(department);
    }

    public List<Patient> getPatienByDoctorStatus(Optional<EmployeeStatus> status ) {
        return patientRepository.findByDoctorStatus(status);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(int id, Patient patient) {
        Optional<Patient> patientOptional = Optional.ofNullable(patientRepository.findByPatientId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")));
        patient.setPatientId(patientOptional.get().getPatientId());
        return patientRepository.save(patient);
    }
}
