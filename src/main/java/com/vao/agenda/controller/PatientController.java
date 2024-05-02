package com.vao.agenda.controller;

import com.vao.agenda.entity.Patient;
import com.vao.agenda.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
