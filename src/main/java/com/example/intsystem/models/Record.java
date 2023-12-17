package com.example.intsystem.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Record {
    public Record(){}

    public Record(Patient patient, Doctor doctor, Date dateTime, String diagnosis, String examinations) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.diagnosis = diagnosis;
        this.examinations = examinations;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private Doctor doctor;

    @Temporal(TemporalType.DATE)
    private Date dateTime;

    @Lob
    private String diagnosis;
    @Lob
    private String examinations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getExaminations() {
        return examinations;
    }

    public void setExaminations(String examinations) {
        this.examinations = examinations;
    }
}
