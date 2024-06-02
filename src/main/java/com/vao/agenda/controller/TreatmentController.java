package com.vao.agenda.controller;

import com.vao.agenda.dto.TreatmentDTO;
import com.vao.agenda.entity.Treatment;
import com.vao.agenda.service.TreatmentService;
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
@RequestMapping("/treatment")
@RestController
public class TreatmentController {

    private final TreatmentService treatmentService;

    @GetMapping
    public Iterable<Treatment> list(){
        return treatmentService.findAll();
    }

    @GetMapping("/{id}")
    public Treatment get(@PathVariable Integer id) {
        return treatmentService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Treatment create(@Validated @RequestBody TreatmentDTO treatmentDTO){
        return treatmentService.create(treatmentDTO);
    }

    @PutMapping("/{id}")
    public Treatment update(@PathVariable Integer id,
                            @Validated @RequestBody TreatmentDTO treatmentDTO){

        return treatmentService.update(id, treatmentDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        treatmentService.delete(id);
    }

}
