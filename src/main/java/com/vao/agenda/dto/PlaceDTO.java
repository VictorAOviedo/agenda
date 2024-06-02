package com.vao.agenda.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;


@Getter
@Setter
public class PlaceDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotNull(message = "Los dias disponibles son obligatorio")
    private List<DayOfWeek> availableDays;
}
