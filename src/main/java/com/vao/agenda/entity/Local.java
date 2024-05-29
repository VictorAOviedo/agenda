package com.vao.agenda.entity;



import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.List;

@Entity
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nombre;
    @NonNull
    @ElementCollection
    private List<DayOfWeek> diasDisponibles; // Lunes=2, Martes=3, ..., Domingo=1


    // Constructor, getters y setters
    public Local() {}

    public Local(String nombre, List<DayOfWeek> diasDisponibles) {
        this.nombre = nombre;
        this.diasDisponibles = diasDisponibles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<DayOfWeek> getDiasDisponibles() {
        return diasDisponibles;
    }

    public void setDiasDisponibles(List<DayOfWeek> diasDisponibles) {
        this.diasDisponibles = diasDisponibles;
    }
}

