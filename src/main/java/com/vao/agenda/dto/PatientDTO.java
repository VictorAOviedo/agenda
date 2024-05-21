package com.vao.agenda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Max(message = "El codigo de area debe tener un maximo de 4 caracteres", value = 9999)
    @Min(message = "El codigo de area debe tener un minimo de 2 caracteres", value = 10)
    private Integer cod;
    @NotNull (message = "El telefono es obligatorio")
    @Max(message = "El telefono debe tener un maximo de 8 caracteres", value = 99999999)
    @Min(message = "El telefono debe tener un minimo de 6 caracteres", value = 100000)
    private Integer phone;
    @Email(message = "El email es inválido")
    private String email;
}



