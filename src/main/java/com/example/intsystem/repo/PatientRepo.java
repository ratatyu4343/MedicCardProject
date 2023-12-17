package com.example.intsystem.repo;

import com.example.intsystem.models.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepo extends CrudRepository<Patient, Long> {
    @Query(value = "select Patient.adress from Patient", nativeQuery = true)
    List<Object> allPatientsAdress();
}
