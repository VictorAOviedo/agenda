package com.vao.agenda.controller;

import com.vao.agenda.entity.Patient;
import com.vao.agenda.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RequestMapping("/patient")
@RestController
public class PatientController {

    @Autowired
    private IPatientRepository iPatientRepository;

    @GetMapping
    public Iterable<Patient> list(){
        return iPatientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Patient> get(@PathVariable Integer id) {
        return iPatientRepository
                .findById(id);
    }

    @PostMapping
    public Patient create(@RequestBody Patient patient){
        return iPatientRepository.save(patient);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Integer id,
                          @RequestBody Patient formP){
        Patient patientFramDB = iPatientRepository
                .findById(id)
                .orElse(null);

            patientFramDB.setName(formP.getName());
            patientFramDB.setLastName(formP.getLastName());
            patientFramDB.setPhone(formP.getPhone());

        return iPatientRepository.save(patientFramDB);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        Patient patientfronDB = iPatientRepository
                .findById(id)
                .orElse(null);

        iPatientRepository.delete(patientfronDB);
    }


}
