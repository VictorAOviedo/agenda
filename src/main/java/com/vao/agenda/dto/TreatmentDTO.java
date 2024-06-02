package com.vao.agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TreatmentDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotNull(message = "La duración del treatment es obligatoria (numero en minutos")
    private int duration;
}
