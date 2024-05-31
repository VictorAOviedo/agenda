package com.vao.agenda.controller;


import com.vao.agenda.dto.ReservaDTO;
import com.vao.agenda.entity.Local;
import com.vao.agenda.entity.Reserva;
import com.vao.agenda.entity.Tratamiento;
import com.vao.agenda.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ReservasController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/reservas")
    public Iterable<Reserva> list(){
        return reservaService.findAll();
    }

    @GetMapping("/reservas/{id}")
    public Reserva get(@PathVariable Integer id) {
        return reservaService.findById(id);
    }

    @GetMapping("/tratamientos")
    public List<Tratamiento> getTratamientos() {
        return reservaService.getTratamientos();
    }

    @GetMapping("/locales")
    public List<Local> getLocales() {
        return reservaService.getLocales();
    }

    @GetMapping("/horarios")
    public ReservaDTO getHorarios(@RequestParam String local, @RequestParam String tratamiento) {
        return reservaService.getHorarios(local, tratamiento);
    }

    @PostMapping("/reservas")
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva create(@RequestBody ReservaDTO reservaDTO) {
        return reservaService.create(reservaDTO);
    }

    @PutMapping("/reservas/{id}")
    public Reserva update(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO) {
        return reservaService.update(id, reservaDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("reservas/{id}")
    public void delete(@PathVariable Long id) {
        reservaService.delete(id);
    }

}
