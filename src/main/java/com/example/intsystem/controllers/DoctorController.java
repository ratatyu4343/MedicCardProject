package com.example.intsystem.controllers;

import com.example.intsystem.models.Doctor;
import com.example.intsystem.repo.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorController {
    @Autowired
    private DoctorRepo doctorRepo;

    @GetMapping(value = "/admin")
    public String showDoctors(@RequestParam(required = false) String organization, Model model) {
        Iterable<Doctor> doctors;
        if(organization == null || organization.equals("")) {
            doctors = doctorRepo.findAll();
        } else{
            doctors = doctorRepo.findDoctorsByOrganization(organization);
        }
        model.addAttribute("organizations", doctorRepo.allOrganizations());
        model.addAttribute("doctors", doctors);
        return "admin/admin_doctors";
    }

    @GetMapping(value = "/admin/add-doctor")
    public String showForm(Model model){
        model.addAttribute("organizations", doctorRepo.allOrganizations());
        model.addAttribute("spetializations", doctorRepo.allSpecialization());
        return "admin/admin_add_doctor";
    }

    @PostMapping(value = "/admin/add-doctor")
    public String addDoctor(@RequestParam String firstname,
                            @RequestParam String lastname,
                            @RequestParam String organization,
                            @RequestParam String spetialization,
                            Model model){
        Doctor doctor = new Doctor(lastname, firstname, spetialization, organization);
        doctorRepo.save(doctor);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/doctor/{id}/edit")
    public String editDoctorShow(@PathVariable(value = "id") long id, Model model){
        if(!doctorRepo.existsById(id)){
            return "redirect:/admin";
        }
        Doctor doctor = doctorRepo.findById(id).get();
        model.addAttribute("doctor", doctor);
        model.addAttribute("organizations", doctorRepo.allOrganizations());
        model.addAttribute("spetializations", doctorRepo.allSpecialization());
        return "admin/admin_doctor_update";
    }

    @PostMapping("/admin/doctor/{id}/edit")
    public String editDoctorUpdate(@PathVariable(value = "id") long id,
                                   @RequestParam String firstname,
                                   @RequestParam String lastname,
                                   @RequestParam String organization,
                                   @RequestParam String spetialization,
                                   Model model){
        Doctor doctor = doctorRepo.findById(id).orElseThrow();
        doctor.setFirst_name(firstname);
        doctor.setLast_name(lastname);
        doctor.setOrganization(organization);
        doctor.setSpecialization(spetialization);
        doctorRepo.save(doctor);

        return "redirect:/admin";
    }

    @GetMapping("/admin/doctor/{id}/remove")
    public String deleteDoctor(@PathVariable(value = "id") long id, Model model){
        doctorRepo.deleteById(id);
        return "redirect:/admin";
    }


}
