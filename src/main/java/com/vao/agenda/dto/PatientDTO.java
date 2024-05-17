package com.vao.agenda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private String name;
    private String lastName;
    private Integer cod;
    private Integer phone;
    private String email;
}
