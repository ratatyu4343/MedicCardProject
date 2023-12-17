package com.example.intsystem.controllers;

import com.example.intsystem.models.Doctor;
import com.example.intsystem.models.Patient;
import com.example.intsystem.models.Record;
import com.example.intsystem.repo.PatientRepo;
import com.example.intsystem.repo.RecordRepo;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Controller
public class PatientController {
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    RecordRepo recorddRepo;

    @GetMapping(value = "doctor/patients")
    public String allPatients(@RequestParam(required = false) String firstname,
                              @RequestParam(required = false) String secondname,
                              Model model){
        Iterable<Patient> patients;
        if(firstname == null || firstname.equals("") || secondname == null || secondname.equals("")) {
            patients = patientRepo.findAll();
        } else{
            patients = patientRepo.findAll();
            List<Patient> patients2 = new ArrayList<>();
            for (Patient p : patients){
                if(p.getFirst_name().equals(firstname) && p.getLast_name().equals(secondname)){
                    patients2.add(p);
                }
            }
            patients = patients2;
        }
        model.addAttribute("patients", patients);
        return "doctor/doctor_patients";
    }

    @GetMapping(value = "/doctor/add-patient")
    public String addPatientShow(Model model){
        model.addAttribute("adresses", patientRepo.allPatientsAdress());
        return "doctor/doctor_add_patient";
    }

    @PostMapping(value = "/doctor/add-patient")
    public String savePatient(@RequestParam String firstname,
                              @RequestParam String lastname,
                              @RequestParam Date birthdate,
                              @RequestParam String adress,
                              Model model){
        Patient patient = new Patient(lastname, firstname, birthdate, adress);
        patientRepo.save(patient);
        return "redirect:/doctor/patients";
    }

    @GetMapping(value = "/doctor/patient/{id}/edit")
    public String editPat(@PathVariable(value = "id") long id, Model model){
        if(!patientRepo.existsById(id)) {
            return "/doctor/patients";
        }
        Patient patient = patientRepo.findById(id).orElseThrow();
        model.addAttribute("patient", patient);
        return "/doctor/doctor_update_patient";
    }

    @PostMapping(value = "/doctor/patient/{id}/edit")
    public String editUpdate(@PathVariable(value = "id") long id,
                             @RequestParam String firstname,
                             @RequestParam String lastname,
                             @RequestParam Date birthdate,
                             @RequestParam String adress,
                             Model model){
        Patient patient = patientRepo.findById(id).orElseThrow();
        patient.setFirst_name(firstname);
        patient.setLast_name(lastname);
        patient.setAdress(adress);
        patient.setBirthdate(birthdate);
        patientRepo.save(patient);

        return "redirect:/doctor/patients";
    }

    @GetMapping(value = "/user/{id}")
    public String showRecords(@PathVariable(value = "id") long id,
                              @RequestParam String datestart,
                              @RequestParam String dateend,
                              Model model){
        Patient patient = patientRepo.findById(id).orElseThrow();
        model.addAttribute("patient", patient);
        List<Record> records = new ArrayList<>();
        for(Record r : recorddRepo.findAll()){
            if(r.getPatient().getId() == id){
                records.add(r);
            }
        }
        if(datestart==null||dateend==null||datestart.equals("")||dateend.equals("")) {
            model.addAttribute("records", records);
        }
        else{
            List<Record> rek = new ArrayList<>();
            for(Record r : records){
                try{
                    java.sql.Date dateS = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(datestart).getTime());
                    java.sql.Date dateE = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateend).getTime());
                    if(r.getDateTime().after(dateS) && r.getDateTime().before(dateE)
                            || r.getDateTime().compareTo(dateS) == 0 || r.getDateTime().compareTo(dateE)==0){
                        rek.add(r);
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            model.addAttribute("records", rek);
        }
        return "user/recordes";
    }
}
