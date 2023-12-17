package com.example.intsystem.repo;

import com.example.intsystem.models.Medication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedizationRepo extends CrudRepository<Medication, Long> {

    List<Medication> findMedicationsByNameStartingWith(String name);
    @Query(value = "select name from Medication", nativeQuery = true)
    List<Object> allNames();
}
