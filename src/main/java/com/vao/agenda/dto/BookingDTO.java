package com.vao.agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BookingDTO {
    @NotBlank(message = "El place es obligatorio")
    private String place;
    @NotBlank (message = "El tipo de treatment es obligatorio")
    private String treatment;
    @NotNull(message = "La fecha y hora es obligatoria")
    private List<String> dateHour;
    @NotNull (message = "El ID del paciente es obligatorio")
    private Long patientId;


    // Constructor, getters y setters
    public BookingDTO() {
    }

    public BookingDTO(String place, String treatment, List<String> dateHour, Long patientId) {
        this.place = place;
        this.treatment = treatment;
        this.dateHour = dateHour;
        this.patientId = patientId;
    }
}


