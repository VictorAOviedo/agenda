package com.vao.agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TratamientoDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotNull(message = "La duración del tratamiento es obligatoria (numero en minutos")
    private int duracion;
}
