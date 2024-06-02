package com.vao.agenda.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String place;
    @NonNull
    private String treatment;
    @NonNull
    private LocalDateTime dateHour;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public Booking() {
    }

    public Booking(Long id, @NonNull String place, @NonNull String treatment, @NonNull LocalDateTime dateHour, Patient patient) {
        this.id = id;
        this.place = place;
        this.treatment = treatment;
        this.dateHour = dateHour;
        this.patient = patient;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateHour = dateHour;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", lugar='" + place + '\'' +
                ", tratamiento='" + treatment + '\'' +
                ", fecha y hora=" + dateHour + '\'' +
                ", paciente=" + patient +
                '}';
    }
}
