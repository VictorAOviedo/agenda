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
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    @ElementCollection
    private List<DayOfWeek> availableDays; // Lunes=0 || MONDAY,..., Domingo=6 || SUNDAY


    // Constructor, getters y setters
    public Place() {}

    public Place(String name, List<DayOfWeek> availableDays) {
        this.name = name;
        this.availableDays = availableDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }
}

