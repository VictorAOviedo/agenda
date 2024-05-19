package com.vao.agenda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    @NotBlank (message = "El nombre es obligatorio")
    private String name;
    @NotBlank (message = "El apellido es obligatorio")
    private String lastName;
    @NotNull (message = "El codigo de area es obligatorio")
    private Integer cod;
    @NotNull (message = "El telefono es obligatorio")
    private Integer phone;

    @Email(message = "El email es inválido")
    private String email;
}
