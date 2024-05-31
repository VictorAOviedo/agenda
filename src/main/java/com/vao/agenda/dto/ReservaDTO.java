package com.vao.agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ReservaDTO {
    @NotBlank(message = "El local es obligatorio")
    private String local;
    @NotBlank (message = "El tipo de tratamiento es obligatorio")
    private String tratamiento;
    @NotNull(message = "La fecha y hora es obligatoria")
    private List<String> fechaHora;
    @NotNull (message = "El ID del paciente es obligatorio")
    private Long patientId;


    // Constructor, getters y setters
    public ReservaDTO() {
    }

    public ReservaDTO(String local, String tratamiento, List<String> fechaHora, Long patientId) {
        this.local = local;
        this.tratamiento = tratamiento;
        this.fechaHora = fechaHora;
        this.patientId = patientId;
    }
}


