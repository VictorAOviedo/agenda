package com.vao.agenda.controller;


import com.vao.agenda.dto.HorariosDTO;
import com.vao.agenda.entity.Local;
import com.vao.agenda.entity.Tratamiento;
import com.vao.agenda.service.ReservasService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ReservasController {

    @Autowired
    private ReservasService reservasService;

    @GetMapping("/tratamientos")
    public List<Tratamiento> getTratamientos() {
        return reservasService.getTratamientos();
    }

    @GetMapping("/locales")
    public List<Local> getLocales() {
        return reservasService.getLocales();
    }

    @GetMapping("/horarios")
    public HorariosDTO getHorarios(@RequestParam String local, @RequestParam String tratamiento) {
        return reservasService.getHorarios(local, tratamiento);
    }
}
