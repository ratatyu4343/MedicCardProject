package com.example.intsystem.repo;

import com.example.intsystem.models.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepo extends CrudRepository<Doctor, Long> {
    @Query(value = "select Doctor.organization from Doctor", nativeQuery = true)
    List<Object> allOrganizations();
    @Query(value = "select Doctor.specialization from Doctor", nativeQuery = true)
    List<Object> allSpecialization();

    List<Doctor> findDoctorsByOrganization(String organization);
}
