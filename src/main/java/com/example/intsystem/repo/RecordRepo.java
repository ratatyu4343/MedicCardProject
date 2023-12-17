package com.example.intsystem.repo;

import com.example.intsystem.models.Doctor;
import com.example.intsystem.models.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RecordRepo extends CrudRepository<Record, Long> {
}
