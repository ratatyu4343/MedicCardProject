package com.example.intsystem.models;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;


@Entity
public class Patient {
    public Patient(){}

    public Patient(String last_name, String first_name, Date birthdate, String adress) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.birthdate = birthdate;
        this.adress = adress;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String last_name;
    private String first_name;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    private String adress;

    @OneToMany
    @JoinColumn
    private List<Record> records;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
