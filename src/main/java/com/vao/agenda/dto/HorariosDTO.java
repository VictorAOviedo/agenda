package com.vao.agenda.dto;

import java.util.List;

public class HorariosDTO {
    private String local;
    private String tratamiento;
    private List<String> horarios;

    // Constructor, getters y setters
    public HorariosDTO() {}

    public HorariosDTO(String local, String tratamiento, List<String> horarios) {
        this.local = local;
        this.tratamiento = tratamiento;
        this.horarios = horarios;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }
}


