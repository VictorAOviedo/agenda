package com.vao.agenda.controller;

import com.vao.agenda.dto.PatientDTO;
import com.vao.agenda.entity.Patient;
import com.vao.agenda.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@AllArgsConstructor
@RequestMapping("/patient")
@RestController
public class PatientController {

    private final PatientService patientService;

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
    public Patient create(@Validated @RequestBody PatientDTO patientDTO){

        return patientService.create(patientDTO);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Integer id,
                          @Validated @RequestBody PatientDTO patientDTO){

        return patientService.update(id, patientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        patientService.delete(id);
    }

}
