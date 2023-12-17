package com.example.intsystem.controllers;


import com.example.intsystem.models.Doctor;
import com.example.intsystem.models.Patient;
import com.example.intsystem.models.Record;
import com.example.intsystem.repo.DoctorRepo;
import com.example.intsystem.repo.MedizationRepo;
import com.example.intsystem.repo.PatientRepo;
import com.example.intsystem.repo.RecordRepo;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class StatisticController {
    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private MedizationRepo medRepo;

    @Autowired
    private RecordRepo recordRepo;

    @GetMapping("/admin/stat/total")
    public String totstat(Model model){
        model.addAttribute("totalmedics", IterableUtils.size(doctorRepo.findAll()));
        model.addAttribute("totalusers", IterableUtils.size(patientRepo.findAll()));
        model.addAttribute("totaltabletki", IterableUtils.size(medRepo.findAll()));
        model.addAttribute("totalrecords", IterableUtils.size(recordRepo.findAll()));
        return "admin/stat/total_count";
    }

    @GetMapping("/admin/stat/med/{id}")
    public String medStat(@RequestParam(required = false) String datestart,
                          @RequestParam(required = false) String dateend,
                          @PathVariable(value = "id") long id,
                          Model model){
        Doctor doctor = doctorRepo.findById(id).orElseThrow();
        model.addAttribute("datestart", datestart);
        model.addAttribute("dateend", dateend);
        model.addAttribute("doctor", doctor);
        if(datestart==null||dateend==null||datestart.equals("")||dateend.equals("")) {
            model.addAttribute("records", IterableUtils.size(recordRepo.findAll()));
        }
        else{
            System.out.println(datestart);
            List<Record> rek = new ArrayList<>();
            Iterable<Record> rel = recordRepo.findAll();
            for(Record r : rel){
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
            model.addAttribute("records", rek.size());
        }

        return "admin/stat/med_stat";
    }

    @GetMapping("/admin/stat/user/{id}")
    public String userStat(@RequestParam(required = false) Data datestart,
                           @RequestParam(required = false) Data dateend,
                           @PathVariable(value = "id") long id,
                           Model model){
        Patient patient = patientRepo.findById(id).orElseThrow();
        model.addAttribute("patient", patient.getRecords().size());
        return "admin/stat/user_stat";
    }

}
