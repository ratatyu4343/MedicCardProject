package com.example.intsystem.controllers;

import com.example.intsystem.models.Doctor;
import com.example.intsystem.models.Patient;
import com.example.intsystem.models.Record;
import com.example.intsystem.repo.DoctorRepo;
import com.example.intsystem.repo.PatientRepo;
import com.example.intsystem.repo.RecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecordController {
    @Autowired
    private RecordRepo recordRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorRepo doctorRepo;

    @GetMapping(value ="/doctor/records")
    public String recordsShowAll(@RequestParam(required = false) String datestart,
                                 @RequestParam(required = false) String dateend,
                                 Model model){
        Doctor doc = doctorRepo.findById(1L).orElseThrow();
        if(datestart==null||dateend==null||datestart.toString().equals("")||dateend.toString().equals("")) {
            model.addAttribute("records", recordRepo.findAll());
        }
        else{
            List<Record> rek = new ArrayList<>();
            Iterable<Record> rel = recordRepo.findAll();
            for(Record r : rel){
                try{
                    Date dateS = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(datestart).getTime());
                    Date dateE = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateend).getTime());
                    if(r.getDateTime().after(dateS) && r.getDateTime().before(dateE)
                            || r.getDateTime().compareTo(dateS) == 0 || r.getDateTime().compareTo(dateE)==0){
                        rek.add(r);
                    }
                } catch (Exception e){
                    System.out.println(e);
                }
            }
            model.addAttribute("records", rek);
        }
        return "doctor/records";
    }

    @GetMapping(value = "/doctor/patient/{id}/add-record")
    public String recordAdd(@PathVariable(value = "id") long id, Model model){
        model.addAttribute("user_id", id);
        model.addAttribute("doctor_id", 1);
        model.addAttribute("patient", patientRepo.findById(id).orElseThrow());

        return "/doctor/doctor_add_record";
    }

    @PostMapping(value = "/doctor/patient/{id}/add-record")
    public String addSave(@PathVariable(value = "id") long id,
                         @RequestParam String diagnosis,
                         @RequestParam String examinations,
                         Model model){

        Patient patient = patientRepo.findById(id).orElseThrow();
        Record record = new Record(patient, doctorRepo.findById(1L).orElseThrow(), new Date(System.currentTimeMillis()), diagnosis, examinations);
        recordRepo.save(record);
        return "redirect:/doctor/patients";
    }
    @GetMapping(value = "/doctor/record/{id}/edit")
    public String recorupdate(@PathVariable(value = "id") long id, Model model){
        model.addAttribute("record", recordRepo.findById(id).orElseThrow());
        model.addAttribute("patient", recordRepo.findById(id).orElseThrow().getPatient());
        return "/doctor/update_record";
    }

    @PostMapping(value = "/doctor/record/{id}/edit")
    public String addSaveupdate(@PathVariable(value = "id") long id,
                              @RequestParam String diagnosis,
                              @RequestParam String examinations,
                                Model model){

        Record record = recordRepo.findById(id).orElseThrow();
        record.setDiagnosis(diagnosis);
        record.setExaminations(examinations);
        recordRepo.save(record);
        return "redirect:/doctor/records";
    }

    @GetMapping(value = "/doctor/record/remove/{id}")
    public String delRecord(@PathVariable(value = "id") long id, Model model){
        recordRepo.deleteById(id);
        return "redirect:/doctor/records";
    }
}
