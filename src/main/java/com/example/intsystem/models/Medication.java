package com.example.intsystem.models;
import jakarta.persistence.*;

@Entity
public class Medication {
    public Medication(){}

    public Medication(String name, String description, String instructions) {
        this.name = name;
        this.description = description;
        this.instructions = instructions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationId;
    private String name;
    @Lob
    private String description;
    @Lob
    private String instructions;

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}