package com.example.intsystem.controllers;

import com.example.intsystem.models.Doctor;
import com.example.intsystem.models.Medication;
import com.example.intsystem.repo.DoctorRepo;
import com.example.intsystem.repo.MedizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MedicineController {
    @Autowired
    private MedizationRepo medRepo;

    @GetMapping(value = "/medicine")
    public String showMedicine(@RequestParam(required = false) String medicine, Model model) {
        Iterable<Medication> medications;
        if(medicine == null || medicine.equals("")) {
            medications = medRepo.findAll();
        } else{
            medications = medRepo.findAll(); //medRepo.findMedicationsByNameStartingWith(medicine);
        }
        model.addAttribute("medicines_name", medRepo.allNames());
        model.addAttribute("medicines", medications);
        return "medications";
    }

    @GetMapping(value = "/admin/add-medicine")
    public String addMed(Model model){return "admin/admin_add_medicine";}

    @PostMapping(value = "/admin/add-medicine")
    public String saveMed(@RequestParam String name,
                          @RequestParam String description,
                          @RequestParam String instructions,
                          Model model){
        Medication medication = new Medication(name, description, instructions);
        medRepo.save(medication);
        return "redirect:/medicine";
    }

    @GetMapping(value = "/medicine/{id}")
    public String showMed(@PathVariable long id, Model model){
        if(!medRepo.existsById(id))
            return "/medications";

        Medication medication = medRepo.findById(id).orElseThrow();
        model.addAttribute("medicine", medication);
        return "medication";
    }

}
