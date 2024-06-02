package com.vao.agenda.controller;


import com.vao.agenda.dto.PlaceDTO;
import com.vao.agenda.entity.Place;
import com.vao.agenda.service.PlaceService;
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
@RequestMapping("/place")
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public Iterable<Place> list(){
        return placeService.findAll();
    }

    @GetMapping("/{id}")
    public Place get(@PathVariable Integer id) {
        return placeService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Place create(@Validated @RequestBody PlaceDTO placeDTO){

        return placeService.create(placeDTO);
    }

    @PutMapping("/{id}")
    public Place update(@PathVariable Integer id,
                        @Validated @RequestBody PlaceDTO placeDTO){

        return placeService.update(id, placeDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Integer id){
        placeService.delete(id);
    }
}
