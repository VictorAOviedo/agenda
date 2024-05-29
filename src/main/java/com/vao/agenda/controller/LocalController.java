package com.vao.agenda.controller;


import com.vao.agenda.dto.LocalDTO;
import com.vao.agenda.entity.Local;
import com.vao.agenda.service.LocalService;
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
@RequestMapping("/local")
@RestController
public class LocalController {

    private final LocalService localService;

    @GetMapping
    public Iterable<Local> list(){
        return localService.findAll();
    }

    @GetMapping("/{id}")
    public Local get(@PathVariable Integer id) {
        return localService.findById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Local create(@Validated @RequestBody LocalDTO localDTO){

        return localService.create(localDTO);
    }


    @PutMapping("/{id}")
    public Local update(@PathVariable Integer id,
                          @Validated @RequestBody LocalDTO localDTO){

        return localService.update(id, localDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        localService.delete(id);
    }
}
