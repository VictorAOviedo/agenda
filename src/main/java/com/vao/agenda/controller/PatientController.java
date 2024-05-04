package com.vao.agenda.controller;

import com.vao.agenda.entity.Patient;
import com.vao.agenda.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RequestMapping("/patient")
@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public Iterable<Patient> list(){
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public Patient get(@PathVariable Integer id) {
        return patientService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Patient create(@RequestBody Patient patient){
        return patientService.create(patient);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Integer id,
                          @RequestBody Patient formP){

        return patientService.update(id, formP);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        patientService.delete(id);
    }


}
