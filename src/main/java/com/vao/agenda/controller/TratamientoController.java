package com.vao.agenda.controller;

import com.vao.agenda.dto.TratamientoDTO;
import com.vao.agenda.entity.Tratamiento;
import com.vao.agenda.service.TratamientoService;
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
@RequestMapping("/tratamiento")
@RestController
public class TratamientoController {

    private final TratamientoService tratamientoService;

    @GetMapping
    public Iterable<Tratamiento> list(){
        return tratamientoService.findAll();
    }

    @GetMapping("/{id}")
    public Tratamiento get(@PathVariable Integer id) {
        return tratamientoService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Tratamiento create(@Validated @RequestBody TratamientoDTO tratamientoDTO){
        return tratamientoService.create(tratamientoDTO);
    }

    @PutMapping("/{id}")
    public Tratamiento update(@PathVariable Integer id,
                          @Validated @RequestBody TratamientoDTO tratamientoDTO){

        return tratamientoService.update(id, tratamientoDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        tratamientoService.delete(id);
    }


}
